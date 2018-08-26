package cn.com.todayfarm.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.deploy.LoginConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.com.todayfarm.dom.ResultObj;
import cn.com.todayfarm.dom.User;
import cn.com.todayfarm.service.UserService;
import sun.misc.BASE64Encoder;

@Controller
public class UserController {
	
	@Autowired
	@Qualifier("userService")//指明需要注入的具体类型
	private UserService userService;
	
	//  注册 登陆 编辑
	/**
	 * 注册方法
	 * @param request
	 *  POST:
	 *  	name String
	 *  	passwordmd5 String
	 *  	key String 注册的时候要输入的一个key，默认是 quningning
	 * @return
	 */
	@RequestMapping(value="/register",method = RequestMethod.POST)
	@ResponseBody
	public Object register(HttpServletRequest request) {
		ResultObj<User> resultObj = new ResultObj<>();
		
		
		String name = request.getParameter("username");
		String passwordmd5 = request.getParameter("passwordmd5");
		String key = request.getParameter("key");
		
		if("quningning".equals(key)) {
			//口令正确
			resultObj.setCode(200);
			String token = ""+System.currentTimeMillis();
			try {
				token = EncoderByMd5( name + System.currentTimeMillis());
				
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int rescode = userService.register(name, passwordmd5,token);
			if (rescode==1) {
				User user = new User();
				user.setUsername(name);
				user.setToken(token);
				resultObj.setData(user);
			}else {
				resultObj.setCode(902);
				resultObj.setMsg("注册新用户入库失败");
			}
//			System.out.println("userservice.register: "+ rescode);
//			resultObj.setMsg("mm:"+rescode+" "+name+" "+passwordmd5);
		}else {
			//口令错误，
			resultObj.setCode(901);
			resultObj.setMsg("注册口令不对");
		}
		
		return resultObj;
	}
	
	/**
	 * login
	 * @param request
	 * 		post:
	 * 			username:
	 * 			passwordmd5:
	 * @return user信息
	 */
	@RequestMapping(value="/login",method = RequestMethod.POST)
	@ResponseBody
	public Object Login(HttpServletRequest request) {
		ResultObj<User> resultObj = new ResultObj<>();
		String name = request.getParameter("username");
		String pwmd5 = request.getParameter("passwordmd5");
		User user = userService.login(name, pwmd5);
		
		if(user==null) {
			resultObj.setCode(903);
			resultObj.setMsg("登陆失败");
		}else {
			resultObj.setCode(200);
			resultObj.setData(user);
		}
		
		return resultObj;
	}
	
	/**
	 * 更改密码
	 * @param token
	 * @param request
	 * 		POST
	 * 			oldpw
	 * 			newpw
	 * 		HEADER
	 * 			usertoken
	 * @return
	 */
	@RequestMapping(value="/changePw",method = RequestMethod.POST)
	@ResponseBody
	public Object changePassword(@RequestHeader("usertoken")String token,HttpServletRequest request) {
		ResultObj<User> resultObj = new ResultObj<>();
		String oldpw = request.getParameter("oldpw");
		String newpw = request.getParameter("newpw");
		
		User user = userService.getPwByToken(token);
		if(user==null) {
			resultObj.setCode(904);
			resultObj.setMsg("token失效，重新登陆");
		}else {
			if(user.getPassword().equals(oldpw)) {
				userService.setNewPassword(newpw,user.getId());
				resultObj.setCode(200);
			}else {
				resultObj.setCode(906);
				resultObj.setMsg("原密码有误");
			}
		}
		
		return resultObj;
	}
	
	/**
	 * 修改用户信息
	 * @param request
	 * 		POST
	 * 			address
	 * 			company
	 * 			job
	 * 			phone
	 * 			sex
	 * 		HEADER
	 * 			usertoken
	 * @return
	 */
	@RequestMapping(value="/editUserInfo",method = RequestMethod.POST)
	@ResponseBody
	public Object editUserInfo(@RequestHeader("usertoken")String token, HttpServletRequest request) {
		ResultObj<User> resultObj = new ResultObj<>();
		
		String address = request.getParameter("address");
		String company = request.getParameter("company");
		String job = request.getParameter("job");
		String phone = request.getParameter("phone");
		String sex = request.getParameter("sex");
		
		
		User user = userService.getUserByToken(token);
		if(user!=null) {
			if(address==null) {
				address = user.getAddress();
			}
			if(company==null) {
				company = user.getCompany();
			}
			if(job==null) {
				job = user.getJob();
			}
			if(phone==null) {
				phone = user.getPhone();
			}
			if(sex==null) {
				sex = user.getSex();
			}
			
			//更新操作
			try {
				userService.updateUserById(user.getId()+"", address, company, job, phone, sex);
				resultObj.setCode(200);
			} catch (Exception e) {
				// TODO: handle exception
				resultObj.setCode(905);
				resultObj.setMsg("用户信息更新失败"+e.toString());
			}
			
			
		}else {
			resultObj.setCode(904);
			resultObj.setMsg("token失效，需要重新登陆");
		}
		
		
		return resultObj;
	}
	
	
	
	 /**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException  
     */
    public String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
	
	
	// test 

//	@Autowired
//	@Qualifier("userService")//指明需要注入的具体类型
//	private UserService userService;
//	
//	public ModelAndView login(
//			String loginname,
//			String password,
//			ModelAndView mv,
//			HttpSession session
//			) {
//		User user = userService.login(loginname, password);
//		if (user != null) {
//			session.setAttribute("user", user);
//			mv.setView(new RedirectView("/fkbookapp/main"));
//		}else {
//			mv.addObject("message","登陆失败");
//			mv.setViewName("loginForm");
//		}
//		return mv;
//	}
}

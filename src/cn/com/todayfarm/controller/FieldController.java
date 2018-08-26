package cn.com.todayfarm.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import cn.com.todayfarm.dom.FarmInfo;
import cn.com.todayfarm.dom.FieldInfo;
import cn.com.todayfarm.dom.ResultObj;
import cn.com.todayfarm.dom.User;
import cn.com.todayfarm.service.FieldService;
import cn.com.todayfarm.service.UserService;
import cn.com.todayfarm.util.PropertyReader;

@Controller
public class FieldController {

	@Autowired
	@Qualifier("fieldService")
	private FieldService fieldService;
	
	@Autowired
	@Qualifier("userService")//指明需要注入的具体类型
	private UserService userService;
	
	/**
	 * 添加农田
	 * POST
	 * 	farmid 农场id
	 * 	name 农田名称
	 * 	area 面积 单位米
	 * 	croptype 农作物种类
	 * 	boundry 边界 geojson
	 * 
	 * @return
	 */
	@RequestMapping(value="/addfield", method = RequestMethod.POST)
	@ResponseBody
	public Object addField(
			@RequestHeader("usertoken")String token,
			HttpServletRequest request
			) {
		ResultObj<FieldInfo> resultObj = new ResultObj<>();
		
		User user = userService.getPwByToken(token);
		
		if(user==null) {
			resultObj.setCode(904);
			resultObj.setMsg("token失效，请重新登陆");
			return resultObj;
		}
		
		String farmid = request.getParameter("farmid");
		String name = request.getParameter("name");
		String area = request.getParameter("area");
		String croptype = request.getParameter("croptype");
		String boundry = request.getParameter("boundry");
		
		//入库
		int res = fieldService.addfield(farmid,name,area,croptype,boundry);
		if(res==1) {
			resultObj.setCode(200);
			
		}else {
			resultObj.setCode(907);
			resultObj.setMsg("添加农田失败");
		}
		
		
		return resultObj;
	}
	
	/**
	 * 获取指定用户下的农田列表
	 * @param token
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getFieldsV2", method = RequestMethod.POST)
	@ResponseBody
	public Object getFieldsv2(
			@RequestHeader("usertoken")String token,
			HttpServletRequest request
			) {
		ResultObj<FieldInfo> resultObj = new ResultObj<>();
		User user = userService.getUserByToken(token);
		if(user==null) {
			resultObj.setCode(904);
			resultObj.setMsg("token失效，重新登陆");
		}else {
			List<FieldInfo> list = fieldService.getfields(user.getId());
			//拼接影像地址
			if (list!=null && list.size()>0) {
				for(int i=0;i<list.size();i++) {
					String growthRelativePath = fieldService.getGrowthRelativePath(list.get(i).getRmGrowthImgId());
					if (growthRelativePath!=null) {
						list.get(i).setGrowthImgPath(
								PropertyReader.getProperty("picrooturl")  +growthRelativePath);
					}
					
					String humidRelativePath = fieldService.getHumidRelativePath(list.get(i).getRmHumidImgId());
					if (humidRelativePath!=null) {
						list.get(i).setHumidImgPath(
								PropertyReader.getProperty("picrooturl")  +humidRelativePath
								);
					}
					
				}
			}
			
			
			resultObj.setCode(200);
			resultObj.setList(list);
		}
		
		
		
		return resultObj;
	}
	
	
	/**
	 * 获取指定用户下的农田列表 ！！！！！过时了 用/getFieldsV2
	 * @param token
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getFields", method = RequestMethod.POST)
	@ResponseBody
	public Object getFields(
			@RequestHeader("usertoken")String token,
			HttpServletRequest request
			) {
		ResultObj<FieldInfo> resultObj = new ResultObj<>();
		User user = userService.getUserByToken(token);
		if(user==null) {
			resultObj.setCode(904);
			resultObj.setMsg("token失效，重新登陆");
		}else {
			List<FieldInfo> list = fieldService.getfields(user.getId());
			//拼接影像地址
			if (list != null && !list.isEmpty()) {
				for(FieldInfo info : list) {
						info.setHumidInfo(fieldService.getHumidImages(info.getRmHumidImgId()));
						info.setGrowthInfo(fieldService.getGrowthImages(info.getRmGrowthImgId()));

				}
			}
			
//			if (list!=null && list.size()>0) {
//				for(int i=0;i<list.size();i++) {
//					String growthRelativePath = fieldService.getGrowthRelativePath(list.get(i).getRmGrowthImgId());
//					if (growthRelativePath!=null) {
//						list.get(i).setGrowthImgPath(
//								PropertyReader.getProperty("picrooturl")  +growthRelativePath);
//					}
//					
//					String humidRelativePath = fieldService.getHumidRelativePath(list.get(i).getRmHumidImgId());
//					if (humidRelativePath!=null) {
//						list.get(i).setHumidImgPath(
//								PropertyReader.getProperty("picrooturl")  +humidRelativePath
//								);
//					}
//					
//				}
//			}
			
			
			resultObj.setCode(200);
			resultObj.setList(list);
		}
		
		
		
		return resultObj;
	}
	
	/**
	 * 添加农场
	 * @param token
	 * @param request
	 *  POST
	 *  	name
	 *  	address
	 * @return
	 */
	@RequestMapping(value="/addfarm", method = RequestMethod.POST)
	@ResponseBody
	public Object addfarm(
			@RequestHeader("usertoken")String token,
			HttpServletRequest request
			) {
		
		System.out.println(request.getCharacterEncoding());
		
		ResultObj<FieldInfo> resultObj = new ResultObj<>();
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		
		User user = userService.getPwByToken(token);
		if(user==null) {
			resultObj.setCode(904);
			resultObj.setMsg("token失效，重新登陆");
		}else {
			int rescode = fieldService.addfarm(user.getId(),name,address);
			if(rescode==1) {
				resultObj.setCode(200);
			}else {
				resultObj.setCode(908);
				resultObj.setMsg("添加农场失败");
			}
		}
		
		
		return resultObj;
	}
	
	/**
	 * 获取用户的农场列表
	 * @param token
	 * @param request
	 * 	POST
	 * 		
	 * @return
	 */
	@RequestMapping(value="/getfarms", method = RequestMethod.POST)
	@ResponseBody
	public Object getfarms(
			@RequestHeader("usertoken")String token,
			HttpServletRequest request
			) {
		ResultObj<FarmInfo> resultObj = new ResultObj<>();
		User user = userService.getPwByToken(token);
		if(user==null) {
			resultObj.setCode(904);
			resultObj.setMsg("token失效，重新登陆");
		}else {
			resultObj.setCode(200);
			List<FarmInfo> list = fieldService.getfarms(user.getId());
			resultObj.setList(list);
		}
		
		
		
		return resultObj;
	}
	
	/**
	 * 获取用户的农场列表
	 * @param token
	 * @param request
	 * 	POST
	 * 		farmid
	 * @return
	 */
	@RequestMapping(value="/delfarm", method = RequestMethod.POST)
	@ResponseBody
	public Object delfarm(
			@RequestHeader("usertoken")String token,
			HttpServletRequest request
			) {
		ResultObj<FarmInfo> resultObj = new ResultObj<>();
		String farmid = request.getParameter("farmid");
		User user = userService.getPwByToken(token);
		if(user==null) {
			resultObj.setCode(904);
			resultObj.setMsg("token失效，重新登陆");
		}else {
			
			int res = fieldService.delfarm(farmid);
			if(res==1) {
				resultObj.setCode(200);
			}else {
				resultObj.setCode(200);
				resultObj.setMsg("无此记录");
			}
			
			
			
		}
		
		
		
		return resultObj;
	}
	
}

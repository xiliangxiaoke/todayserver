package cn.com.todayfarm.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.com.todayfarm.dom.GrowthDataInfo;
import cn.com.todayfarm.dom.HumidDataInfo;
import cn.com.todayfarm.dom.ResultObj;
import cn.com.todayfarm.dom.User;
import cn.com.todayfarm.service.FieldService;
import cn.com.todayfarm.service.UserService;
import cn.com.todayfarm.util.PropertyReader;

@Controller
public class GrowthController {

	@Autowired
	@Qualifier("userService")//指明需要注入的具体类型
	private UserService userService;
	
	@Autowired
	@Qualifier("fieldService")
	private FieldService fieldService;
	
	
	
	/**
	 * 添加长势数据
	 * @param token
	 * @param request
	 * POST
	 * 	fieldid
	 * 	datetime
	 * 	datavalue
	 * @return
	 */
	@RequestMapping(value="/addGrowthData",method=RequestMethod.POST)
	@ResponseBody
	public Object addGrowthData(@RequestHeader("usertoken")String token,
			HttpServletRequest request) {
		ResultObj<String> resultObj = new ResultObj<>();
		
		String fieldid = request.getParameter("fieldid");
		String datetime = request.getParameter("datetime");
		String datavalue = request.getParameter("datavalue");
		
		User user = userService.getUserByToken(token);
		if (user!=null) {
			
			fieldService.addGrowthData(fieldid,datetime,datavalue);
			
			resultObj.setCode(200);
		}else {
			resultObj.setCode(904);
			resultObj.setMsg("token失效，需要重新登陆");
		}
		
		
		return resultObj;
	}
	
	/**
	 * 获取长势数据
	 * @param token
	 * @param request
	 * POST
	 * 	fieldid
	 * 	
	 * 	
	 * @return
	 */
	@RequestMapping(value="/getGrowthData",method=RequestMethod.POST)
	@ResponseBody
	public Object getGrowthData(@RequestHeader("usertoken")String token,
			HttpServletRequest request) {
		ResultObj<GrowthDataInfo> resultObj = new ResultObj<>();
		
		String fieldid = request.getParameter("fieldid");
		
		
		User user = userService.getUserByToken(token);
		if (user!=null) {
			
			List<GrowthDataInfo> list = fieldService.getGrowthData(fieldid);
			
			resultObj.setCode(200);
			resultObj.setList(list);
		}else {
			resultObj.setCode(904);
			resultObj.setMsg("token失效，需要重新登陆");
		}
		
		
		return resultObj;
	}
	
	
	/**
	 * 获取湿度数据
	 * @param token
	 * @param request
	 * POST
	 * 	fieldid
	 * 	
	 * 	
	 * @return
	 */
	@RequestMapping(value="/getHumidData",method=RequestMethod.POST)
	@ResponseBody
	public Object getHumidData(@RequestHeader("usertoken")String token,
			HttpServletRequest request) {
		ResultObj<HumidDataInfo> resultObj = new ResultObj<>();
		
		String fieldid = request.getParameter("fieldid");
		
		
		User user = userService.getUserByToken(token);
		if (user!=null) {
			
			List<HumidDataInfo> list = fieldService.getHumidData(fieldid);
			
			resultObj.setCode(200);
			resultObj.setList(list);
		}else {
			resultObj.setCode(904);
			resultObj.setMsg("token失效，需要重新登陆");
		}
		
		
		return resultObj;
	}
	
	
	/**
	 * 添加湿度数据
	 * @param token
	 * @param request
	 * POST
	 * 	fieldid
	 * 	datetime
	 * 	datavalue
	 * @return
	 */
	@RequestMapping(value="/addHumidData",method=RequestMethod.POST)
	@ResponseBody
	public Object addHumidData(@RequestHeader("usertoken")String token,
			HttpServletRequest request) {
		ResultObj<String> resultObj = new ResultObj<>();
		
		String fieldid = request.getParameter("fieldid");
		String datetime = request.getParameter("datetime");
		String datavalue = request.getParameter("datavalue");
		
		User user = userService.getUserByToken(token);
		if (user!=null) {
			
			fieldService.addHumidData(fieldid,datetime,datavalue);
			
			resultObj.setCode(200);
		}else {
			resultObj.setCode(904);
			resultObj.setMsg("token失效，需要重新登陆");
		}
		
		
		return resultObj;
	}
	
	
	/**
	 * 上传图片
	 * @param request
	 * 	POST
	 * 		fieldid
	 * 		type: 1 长势图  2 湿度图
	 * 		createtime 生成时间
	 * 		extentleft
	 * 		extentbottom
	 * 		extentright
	 * 		extenttop
	 * 		description
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uploadFieldSateliteImg",method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object uploadImg(@RequestHeader("usertoken")String token,
			HttpServletRequest request,
			@RequestParam("file") MultipartFile file
			) throws Exception{
		ResultObj<String> resultObj = new ResultObj<>();
		
		String fieldid= request.getParameter("fieldid");
		String type = request.getParameter("type");
		String extentleft = request.getParameter("extentleft");
		String extentbottom = request.getParameter("extentbottom");
		String extentright = request.getParameter("extentright");
		String extenttop = request.getParameter("extenttop");
		String createtime = request.getParameter("createtime");
		
		User user = userService.getUserByToken(token);
		if (user!=null) {
			if (!file.isEmpty()) {
				// 上传文件类型
				String cpath = "";
				if ("1".equals(type)) {
					cpath = "growth/";
				}else if ("2".equals(type)) {
					cpath = "humid/";
				}else {
					cpath = "unknown/";
				}
				String path = PropertyReader.getProperty("picpath")+cpath; //"/home/todayfarm/data/pics/cropgrowth/";
				String filename = getUUIDFileName(file.getOriginalFilename()); //file.getOriginalFilename();
				File filepath = new File(path,filename);
				
				if (!filepath.getParentFile().exists()) {
					filepath.getParentFile().mkdirs();
				}
				//将上传文件保存到一个目标文件夹中
				file.transferTo(new File(path+filename));
				
				//获取图片url: 保存的是相对路径，是picpath之后的部分
				String savepath = cpath+filename;
				
				//入库并返回指定ID，更新到农田表的图片ID上
				if ("1".equals(type)) {
					fieldService.addFieldGrowthImg(createtime,fieldid,savepath,
							extentleft,extentbottom,extentright,extenttop);
				}else if ("2".equals(type)) {
					fieldService.addFieldHumidImg(createtime,fieldid,savepath,
							extentleft,extentbottom,extentright,extenttop);
				} 
				
				resultObj.setCode(200);
				
			}else {
				resultObj.setCode(909);
				resultObj.setMsg("上传图片失败");
			}
			resultObj.setCode(200);
		}else {
			resultObj.setCode(904);
			resultObj.setMsg("token失效，需要重新登陆");
		}
		
		
		// /home/todayfarm/data/pic/growth
		return resultObj;
	}
	
	
	
	private String getExtName(String s, char split) {    
        int i = s.lastIndexOf(split);    
        int leg = s.length();    
        return i > 0 ? (i + 1) == leg ? " " : s.substring(i+1, s.length()) : " ";    
    }    
    
  private String getUUIDFileName(String fileName){    
         UUID uuid = UUID.randomUUID();    
         StringBuilder sb = new StringBuilder(100);    
         sb.append(uuid.toString()).append(".").append(this.getExtName(fileName, '.'));    
         return sb.toString();    
     }  
    
  private String getRealName(String originalName){  
      //System.out.println(originalName.contains("."));  
        
      if(originalName.contains(".")){  
     String [] as = originalName.split("\\.");  
     //System.out.println(as[0]);  
     return as[0];  
      }else {  
         return originalName;  
     }  
        
  }  
}

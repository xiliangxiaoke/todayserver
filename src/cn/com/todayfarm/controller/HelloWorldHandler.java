package cn.com.todayfarm.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Authenticator.Success;

import cn.com.todayfarm.dom.User;

@Controller
public class HelloWorldHandler {
	
	

	
	
	
	// test methods

	@RequestMapping(value="/hello")
	public String hello() {
		System.out.println("enter helloworld");
		return "success";
	}
	
	@RequestMapping(value="/testjson/{id}")
	public void testjson(@PathVariable String id,HttpServletResponse response)throws Exception{
		User user = new User();
		user.setId(100);
		user.setUsername("testname");
		
		ObjectMapper mapper = new ObjectMapper();
		
		//response.setContentType("text/html;charset=UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(mapper.writeValueAsString(user));
	}
	
	@RequestMapping(value="/testreturnjson/{id}")
	@ResponseBody
	public Object testreturnjson(@PathVariable String id,HttpServletResponse response)throws Exception{
		User user = new User();
		user.setId(100);
		user.setUsername("testname");
		
		return user;
	}
	
}

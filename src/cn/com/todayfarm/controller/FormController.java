package cn.com.todayfarm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 动态页面跳转控制器
 * @author likunshang
 *
 */
@Controller
public class FormController {

	@RequestMapping(value="/{formName}")
	public String loginForm(@PathVariable String formName) {
		//动态页面跳转
		return formName;
	}
}

package com.example.demo.web;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api")
public class TestController {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	/**
	 * 返回带参数的静态页面
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(Model model) throws Exception {
		model.addAttribute("name", "jeson");
		model.addAttribute("currentDate", new Date());
		// if(model != null){
		// throw new Exception();
		// }

		return "list";
	}

}

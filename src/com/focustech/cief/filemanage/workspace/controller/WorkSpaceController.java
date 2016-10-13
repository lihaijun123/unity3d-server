package com.focustech.cief.filemanage.workspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 *
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping("/workspace")
public class WorkSpaceController {

	@RequestMapping(method = RequestMethod.GET)
	public String index(){


		return "/workspace/index";
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(){


		return "/workspace/welcome";
	}

}

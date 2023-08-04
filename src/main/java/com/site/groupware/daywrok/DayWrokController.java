package com.site.groupware.daywrok;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DayWrokController {
	 
	@GetMapping("/daywork/list")
	public String list() {
	    return "daywork_list";
	}
}

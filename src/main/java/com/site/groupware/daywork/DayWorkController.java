package com.site.groupware.daywork;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/daywork")
@RequiredArgsConstructor
@Controller
public class DayWorkController {
	 
    private final DayWorkService dayworkService;
	
    @GetMapping("/list")
	public String list(Model model) {
		List<DayWork> dayworkList = this.dayworkService.getList();
		model.addAttribute("dayworkList",dayworkList);
	    return "daywork_list";
	}
    
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
    	DayWork daywork = this.dayworkService.getDayWork(id);
        model.addAttribute("daywork", daywork);
    	return "daywork_detail";
    }
}

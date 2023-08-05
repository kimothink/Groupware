package com.site.groupware.dayworkanswer;

import com.site.groupware.daywork.DayWork;
import com.site.groupware.daywork.DayWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/dayworkanswer")
@RequiredArgsConstructor
@Controller
public class DayWorkAnswerController {
	
	private final DayWorkService dayworkService;
	
	@PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam String content) {
        DayWork daywork = this.dayworkService.getDayWork(id);
        // TODO: 답변을 저장한다. 
        return String.format("redirect:/daywork/detail/%s", id);
    }

}

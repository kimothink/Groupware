package com.site.groupware.dayworkanswer;

import com.site.groupware.daywork.DayWork;
import com.site.groupware.daywork.DayWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import java.security.Principal;
import com.site.groupware.user.SiteUser;
import com.site.groupware.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;

@RequestMapping("/dayworkanswer")
@RequiredArgsConstructor
@Controller
public class DayWorkAnswerController {
	
	private final DayWorkService dayworkService;
    private final DayWorkAnswerService dayworkanswerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id,
    		@Valid DayWorkAnswerForm dayworkanswerForm, BindingResult bindingResult,
    		Principal principal) {
        DayWork daywork = this.dayworkService.getDayWork(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        
        // TODO: 답변을 저장한다. 
        if (bindingResult.hasErrors()) {
            model.addAttribute("daywork", daywork);
            return "daywork_detail";
        }
        this.dayworkanswerService.create(daywork, dayworkanswerForm.getContent(),siteUser);

        return String.format("redirect:/daywork/detail/%s", id);
    }

}

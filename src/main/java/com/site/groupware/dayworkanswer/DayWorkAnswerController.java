package com.site.groupware.dayworkanswer;


import jakarta.validation.Valid;
import java.security.Principal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.validation.BindingResult;
import org.springframework.security.access.prepost.PreAuthorize;

import com.site.groupware.user.SiteUser;
import com.site.groupware.user.UserService;
import com.site.groupware.daywork.DayWork;
import com.site.groupware.daywork.DayWorkService;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(DayWorkAnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
        DayWorkAnswer answer = this.dayworkanswerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());
        return "dayworkanswer_form";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid DayWorkAnswerForm answerForm, BindingResult bindingResult,
            @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "dayworkanswer_form";
        }
        DayWorkAnswer answer = this.dayworkanswerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.dayworkanswerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/daywork/detail/%s", answer.getDaywork().getId());
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
    	DayWorkAnswer answer = this.dayworkanswerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.dayworkanswerService.delete(answer);
        return String.format("redirect:/daywork/detail/%s", answer.getDaywork().getId());
    }
}

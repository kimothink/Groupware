package com.site.groupware.daywork;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import com.site.groupware.dayworkanswer.DayWorkAnswerForm;
import com.site.groupware.user.SiteUser;
import com.site.groupware.user.UserService;
import java.security.Principal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import lombok.RequiredArgsConstructor;

@RequestMapping("/daywork")
@RequiredArgsConstructor
@Controller
public class DayWorkController {

	private final DayWorkService dayworkService;
	private final UserService userService;

	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<DayWork> paging = this.dayworkService.getList(page,kw);
		model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);

		return "daywork_list";
	}

	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, DayWorkAnswerForm dayworkanswerForm) {
		DayWork daywork = this.dayworkService.getDayWork(id);
		model.addAttribute("daywork", daywork);
		return "daywork_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String dayworkCreate(Model model, DayWorkForm dayworkForm) {

		model.addAttribute("dayWorkForm", dayworkForm);
		return "daywork_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String dayworkCreate(@Valid DayWorkForm dayworkForm, BindingResult bindingResult, Principal principal) {

		if (bindingResult.hasErrors()) {
			return "daywork_form";
		}
		SiteUser siteUser = this.userService.getUser(principal.getName());
		// TODO 질문을 저장한다.
		this.dayworkService.create(dayworkForm.getSubject(), dayworkForm.getContent(), siteUser);

		return "redirect:/daywork/list"; // 질문 저장후 질문목록으로 이동
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(DayWorkForm dayworkForm, @PathVariable("id") Integer id, Principal principal) {
		DayWork question = this.dayworkService.getDayWork(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		dayworkForm.setSubject(question.getSubject());
		dayworkForm.setContent(question.getContent());
		return "daywork_form";
	}
	
	@PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid DayWorkForm dayworkForm, BindingResult bindingResult, 
            Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        DayWork daywork = this.dayworkService.getDayWork(id);
        if (!daywork.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.dayworkService.modify(daywork, dayworkForm.getSubject(), dayworkForm.getContent());
        return String.format("redirect:/daywork/detail/%s", id);
    }
	
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
		DayWork daywork = this.dayworkService.getDayWork(id);
        if (!daywork.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.dayworkService.delete(daywork);
        return "redirect:/";
    }
}

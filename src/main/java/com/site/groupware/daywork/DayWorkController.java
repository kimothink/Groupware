package com.site.groupware.daywork;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import com.site.groupware.dayworkanswer.DayWorkAnswerForm;
import com.site.groupware.user.SiteUser;
import com.site.groupware.user.UserService;
import java.security.Principal;
import org.springframework.security.access.prepost.PreAuthorize;

import lombok.RequiredArgsConstructor;

@RequestMapping("/daywork")
@RequiredArgsConstructor
@Controller
public class DayWorkController {

	private final DayWorkService dayworkService;
    private final UserService userService;

	@GetMapping("/list")
	public String list(Model model,@RequestParam(value="page", defaultValue="0") int page)
	{
		Page<DayWork> paging = this.dayworkService.getList(page);
	    model.addAttribute("paging", paging);
		return "daywork_list";
	}

	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, DayWorkAnswerForm dayworkanswerForm) {
		DayWork daywork = this.dayworkService.getDayWork(id);
		model.addAttribute("daywork", daywork);
		return "daywork_detail";
	}

	@GetMapping("/create")
	public String dayworkCreate(DayWorkForm dayworkForm) {
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
		this.dayworkService.create(dayworkForm.getSubject(), dayworkForm.getContent(),siteUser);

		return "redirect:/daywork/list"; // 질문 저장후 질문목록으로 이동
	}
}

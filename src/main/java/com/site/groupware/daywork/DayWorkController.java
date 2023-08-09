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

import com.site.groupware.dayworkanswer.DayWorkAnswerForm;

import lombok.RequiredArgsConstructor;

@RequestMapping("/daywork")
@RequiredArgsConstructor
@Controller
public class DayWorkController {

	private final DayWorkService dayworkService;

	@GetMapping("/list")
	public String list(Model model) {
		List<DayWork> dayworkList = this.dayworkService.getList();
		model.addAttribute("dayworkList", dayworkList);
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

	@PostMapping("/create")
	public String dayworkCreate(@Valid DayWorkForm dayworkForm, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "daywork_form";
		}

		// TODO 질문을 저장한다.
		this.dayworkService.create(dayworkForm.getSubject(), dayworkForm.getContent());

		return "redirect:/daywork/list"; // 질문 저장후 질문목록으로 이동
	}
}

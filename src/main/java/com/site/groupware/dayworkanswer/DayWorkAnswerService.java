package com.site.groupware.dayworkanswer;

import com.site.groupware.daywork.DayWork;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class DayWorkAnswerService {
	
	private final DayWorkAnswerRepository dayworkRepository;
	
	public void create(DayWork daywork, String content) {
		DayWorkAnswer dayworkanswer = new DayWorkAnswer();
		dayworkanswer.setContent(content);
		dayworkanswer.setCreateDate(LocalDateTime.now());
		dayworkanswer.setDaywork(daywork);
        this.dayworkRepository.save(dayworkanswer);
    }

}

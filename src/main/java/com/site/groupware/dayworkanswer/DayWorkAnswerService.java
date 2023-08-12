package com.site.groupware.dayworkanswer;

import com.site.groupware.daywork.DayWork;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import com.site.groupware.user.SiteUser;

@RequiredArgsConstructor
@Service
public class DayWorkAnswerService {
	
	private final DayWorkAnswerRepository dayworkRepository;
	
	public void create(DayWork daywork, String content, SiteUser author) {
		DayWorkAnswer dayworkanswer = new DayWorkAnswer();
		dayworkanswer.setContent(content);
		dayworkanswer.setCreateDate(LocalDateTime.now());
		dayworkanswer.setDaywork(daywork);
		dayworkanswer.setAuthor(author);
        this.dayworkRepository.save(dayworkanswer);
    }

}

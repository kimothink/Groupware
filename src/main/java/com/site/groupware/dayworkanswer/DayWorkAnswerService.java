package com.site.groupware.dayworkanswer;

import com.site.groupware.daywork.DayWork;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import com.site.groupware.user.SiteUser;
import java.util.Optional;
import com.site.groupware.DataNotFoundException;

@RequiredArgsConstructor
@Service
public class DayWorkAnswerService {
	
	private final DayWorkAnswerRepository dayworkanswerRepository;
	
	public DayWorkAnswer create(DayWork daywork, String content, SiteUser author) {
		DayWorkAnswer dayworkanswer = new DayWorkAnswer();
		dayworkanswer.setContent(content);
		dayworkanswer.setCreateDate(LocalDateTime.now());
		dayworkanswer.setDaywork(daywork);
		dayworkanswer.setAuthor(author);
        this.dayworkanswerRepository.save(dayworkanswer);
        
        return dayworkanswer;
    }
	
	
	 public DayWorkAnswer getAnswer(Integer id) {
	        Optional<DayWorkAnswer> answer = this.dayworkanswerRepository.findById(id);
	        if (answer.isPresent()) {
	            return answer.get();
	        } else {
	            throw new DataNotFoundException("answer not found");
	        }
	    }

	    public void modify(DayWorkAnswer answer, String content) {
	        answer.setContent(content);
	        answer.setModifyDate(LocalDateTime.now());
	        this.dayworkanswerRepository.save(answer);
	    }
	    
	    public void delete(DayWorkAnswer answer) {
	        this.dayworkanswerRepository.delete(answer);
	    }
}

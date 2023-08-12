package com.site.groupware.daywork;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.site.groupware.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
@Service
public class DayWorkService {
	private final DayWrokRepository dayworkRepository;
	
	public List<DayWork> getList()
	{
		return this.dayworkRepository.findAll();
	}
	
	public DayWork getDayWork(Integer id) {  
        Optional<DayWork> question = this.dayworkRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
	
	public Page<DayWork> getList(int page) {
		List<Sort.Order> sorts = new ArrayList<>();
	    sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.dayworkRepository.findAll(pageable);
    }
	
	public void create(String subject, String content) {
        DayWork daywork = new DayWork();
        daywork.setSubject(subject);
        daywork.setContent(content);
        daywork.setCreateDate(LocalDateTime.now());
        this.dayworkRepository.save(daywork);
    }
}

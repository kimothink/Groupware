package com.site.groupware.daywork;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.site.groupware.DataNotFoundException;
import lombok.RequiredArgsConstructor;

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
	
}
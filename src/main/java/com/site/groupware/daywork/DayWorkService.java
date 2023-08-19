package com.site.groupware.daywork;

import com.site.groupware.dayworkanswer.DayWorkAnswer;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.site.groupware.DataNotFoundException;
import com.site.groupware.user.SiteUser;

import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
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
        Optional<DayWork> daywork = this.dayworkRepository.findById(id);
        if (daywork.isPresent()) {
            return daywork.get();
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
	
	public void create(String subject, String content,SiteUser user) {
        DayWork daywork = new DayWork();
        daywork.setSubject(subject);
        daywork.setContent(content);
        daywork.setCreateDate(LocalDateTime.now());
        daywork.setAuthor(user);

        this.dayworkRepository.save(daywork);
    }
	
	public void modify(DayWork daywork, String subject, String content) {
		daywork.setSubject(subject);
		daywork.setContent(content);
		daywork.setModifyDate(LocalDateTime.now());
        this.dayworkRepository.save(daywork);
    }
	
	public void delete(DayWork daywork) {
        this.dayworkRepository.delete(daywork);
    }
}

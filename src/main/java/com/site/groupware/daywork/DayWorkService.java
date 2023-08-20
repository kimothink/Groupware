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
	
	public Page<DayWork> getList(int page,  String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
	    sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<DayWork> spec = search(kw);
        return this.dayworkRepository.findAll(spec,pageable);
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
	
	 private Specification<DayWork> search(String kw) {
	        return new Specification<>() {
	            private static final long serialVersionUID = 1L;
	            @Override
	            public Predicate toPredicate(Root<DayWork> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                query.distinct(true);  // 중복을 제거 
	                Join<DayWork, SiteUser> u1 = q.join("author", JoinType.LEFT);
	                Join<DayWork, DayWorkAnswer> a = q.join("dayworkanswerList", JoinType.LEFT);
	                Join<DayWork, SiteUser> u2 = a.join("author", JoinType.LEFT);
	                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목 
	                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용 
	                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자 
	                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용 
	                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자 
	            }
	        };
	    }
}

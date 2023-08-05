package com.site.groupware.daywork;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayWrokRepository  extends JpaRepository<DayWork, Integer>{
	DayWork findBySubject(String subject);
	DayWork findBySubjectAndContent(String subject, String content);
    List<DayWork> findBySubjectLike(String subject);

}

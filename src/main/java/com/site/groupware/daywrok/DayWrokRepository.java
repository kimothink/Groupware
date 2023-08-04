package com.site.groupware.daywrok;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayWrokRepository  extends JpaRepository<DayWrok, Integer>{
	DayWrok findBySubject(String subject);
	DayWrok findBySubjectAndContent(String subject, String content);
    List<DayWrok> findBySubjectLike(String subject);

}

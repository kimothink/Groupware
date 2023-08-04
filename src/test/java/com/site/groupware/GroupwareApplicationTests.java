package com.site.groupware;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.site.groupware.daywrok.DayWrok;
import com.site.groupware.daywrok.DayWrokRepository;

@SpringBootTest
class GroupwareApplicationTests {

	@Autowired
    private DayWrokRepository dayworkRepository;

    @Test
    void testJpa() {        
    	DayWrok q1 = new DayWrok();
        q1.setSubject("2023-08-04");
        q1.setContent("농ㅈ어보조시스템 개발 ");
        q1.setCreateDate(LocalDateTime.now());
        this.dayworkRepository.save(q1);  // 첫번째 질문 저장

        DayWrok q2 = new DayWrok();
        q2.setSubject("2023-08-05");
        q2.setContent("농정 보조시스템 개발");
        q2.setCreateDate(LocalDateTime.now());
        this.dayworkRepository.save(q2);  // 두번째 질문 저장
    }
	
	
	@Test
	void contextLoads() {
	}

}

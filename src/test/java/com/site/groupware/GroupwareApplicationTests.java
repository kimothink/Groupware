package com.site.groupware;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.site.groupware.daywork.DayWork;
import com.site.groupware.daywork.DayWorkService;
import com.site.groupware.daywork.DayWrokRepository;

@SpringBootTest
class GroupwareApplicationTests {

	@Autowired
    private DayWorkService dayworkService;
	
	
    @Test
    void testJpa() {        
    	 for (int i = 1; i <= 300; i++) {
             String subject = String.format("테스트 데이터입니다:[%03d]", i);
             String content = "내용무";
             this.dayworkService.create(subject, content,null);
         }
    }
	
	
	@Test
	void contextLoads() {
	}

}

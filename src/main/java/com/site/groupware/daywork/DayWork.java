package com.site.groupware.daywork;

import java.time.LocalDateTime;
import java.util.List;

import com.site.groupware.dayworkanswer.DayWorkAnswer;
import com.site.groupware.user.SiteUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DayWork {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Integer id;

	 @Column(length = 200)
	 private String subject;

	 @Column(columnDefinition = "TEXT")
	 private String content;

	 private LocalDateTime createDate;
	  
	 private LocalDateTime modifyDate;

	 @OneToMany(mappedBy = "daywork", cascade = CascadeType.REMOVE)
	 private List<DayWorkAnswer> dayworkanswerList;
	  
	 @ManyToOne
	 private SiteUser author;
}

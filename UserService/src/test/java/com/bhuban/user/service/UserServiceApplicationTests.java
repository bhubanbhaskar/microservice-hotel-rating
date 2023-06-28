package com.bhuban.user.service;

import com.bhuban.user.service.entities.Rating;
import com.bhuban.user.service.external.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
private RatingService ratingService;

	@Test
	void contextLoads() {
	}

//	@Test
//	void createRating(){
//		Rating rating = Rating.builder().rating(10)
//						.userId("").hotelId("").feedback("this is created using feign client").
//				build();
//		ratingService.createRating(rating);
//		System.out.println("new rating created");
//	}


}

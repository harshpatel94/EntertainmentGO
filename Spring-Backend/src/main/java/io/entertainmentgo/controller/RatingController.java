package io.entertainmentgo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.entertainmentgo.entity.Rating;
import io.entertainmentgo.exception.NoTitleFoundException;
import io.entertainmentgo.exception.UserAlreadyRatedTitle;
import io.entertainmentgo.service.RatingService;

@RestController
@RequestMapping(path="/ratings",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RatingController {
	@Autowired
	private RatingService ratingService;

	@RequestMapping(method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Rating rateTitle(@RequestBody Rating rating) throws NoTitleFoundException, UserAlreadyRatedTitle{
		Rating ratedTitle = ratingService.rateTitle(rating);
		return ratedTitle;
	}
	@RequestMapping(method=RequestMethod.GET)
	public List<Rating> findAll() {
		List<Rating> allRatings = ratingService.findAll(); 
		return allRatings;
	}
	
}

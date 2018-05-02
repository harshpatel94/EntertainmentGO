package io.entertainmentgo.service;

import java.util.List;

import io.entertainmentgo.entity.Rating;
import io.entertainmentgo.exception.NoTitleFoundException;
import io.entertainmentgo.exception.UserAlreadyRatedTitle;

public interface RatingService {

	Rating rateTitle(Rating rating) throws NoTitleFoundException, UserAlreadyRatedTitle;

	List<Rating> findAll();

}

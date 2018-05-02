package io.entertainmentgo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.entertainmentgo.entity.Rating;
import io.entertainmentgo.entity.Title;
import io.entertainmentgo.entity.User;
import io.entertainmentgo.exception.NoTitleFoundException;
import io.entertainmentgo.exception.UserAlreadyRatedTitle;
import io.entertainmentgo.exception.UserNotFound;
import io.entertainmentgo.repository.RatingRepository;
import io.entertainmentgo.repository.TitleRepository;
import io.entertainmentgo.repository.UserRepository;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TitleRepository titleRepository;

	@Override
	public Rating rateTitle(Rating rating) throws NoTitleFoundException, UserAlreadyRatedTitle {
		Title existingTitle = titleRepository.findOne(rating.getTitle().getTitleId());
		User existingUser = userRepository.findOne(rating.getUser().getUserId());
		if (existingTitle == null) {
			throw new NoTitleFoundException("No title found with the given ID");
		}
		if (existingUser == null) {
			throw new UserNotFound("No User found with the given ID");
		}
		List<Rating> userRatedTitleList =  checkUserRatedTitle(rating.getTitle().getTitleId(),rating.getUser().getUserId());
		
		if(!userRatedTitleList.isEmpty()){
			throw new UserAlreadyRatedTitle("User rated the title "+existingTitle.getTitle());
		}
		Rating newRatingObj = setUserAndTitle(existingUser, existingTitle, rating);
		Rating persistedRating = ratingRepository.save(newRatingObj);
		Double avgRating = getAveragerating(rating.getTitle().getTitleId());
		existingTitle.setAverageRating(avgRating);
		titleRepository.save(existingTitle);
		return persistedRating;
	}

	private List<Rating> checkUserRatedTitle(String titleId, String userId) {
		List<Rating> userRatedTitleList = ratingRepository.findByTitle_titleIdAndUser_userId(titleId,userId);
		return userRatedTitleList;
	}

	private Double getAveragerating(String titleId) {
		Double avgRating = ratingRepository.findAvgByTitle(titleId);
		return avgRating;
	}

	private Rating setUserAndTitle(User existingUser, Title existingTitle, Rating rating) {
		rating.setTitle(existingTitle);
		rating.setUser(existingUser);
		return rating;
	}

	@Override
	public List<Rating> findAll() {
		List<Rating> allratings = (List<Rating>) ratingRepository.findAll();
		return allratings;
	}

}

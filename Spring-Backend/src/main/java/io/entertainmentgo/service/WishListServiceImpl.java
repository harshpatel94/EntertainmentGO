package io.entertainmentgo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.entertainmentgo.entity.Title;
import io.entertainmentgo.entity.User;
import io.entertainmentgo.entity.WishList;
import io.entertainmentgo.exception.NoTitleFoundException;
import io.entertainmentgo.exception.TitleAlreadyUser;
import io.entertainmentgo.exception.UserNotFound;
import io.entertainmentgo.repository.TitleRepository;
import io.entertainmentgo.repository.UserRepository;
import io.entertainmentgo.repository.WishListRepository;

@Service
@Transactional
public class WishListServiceImpl implements WishListService {
	@Autowired
	private WishListRepository wishListRepository;

	@Autowired
	private TitleRepository titleRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public WishList addTitleToWishList(WishList wishList) {
		User existingUser = userRepository.findOne(wishList.getUser().getUserId());
		Title existingTitle = titleRepository.findOne(wishList.getTitle().getTitleId());
		if (existingTitle == null) {
			throw new NoTitleFoundException("No title found with the given ID");
		}
		if (existingUser == null) {
			throw new UserNotFound("No User found with the given ID");
		}
		List<WishList> checkTitleUsed = checkTitleUsed(wishList.getTitle().getTitleId(), wishList.getUser().getUserId());
		if (!checkTitleUsed.isEmpty()) {
			throw new TitleAlreadyUser("User Added the title " + existingTitle.getTitle() + " to their wishlist");
		}
		WishList settedComment = setProperties(existingUser, existingTitle, wishList);
		WishList persistedWishList = wishListRepository.save(settedComment);
		return persistedWishList;
	}

	private List<WishList> checkTitleUsed(String titleId, String userId) {
		List<WishList> checkTitleUsedList = wishListRepository.findByTitle_titleIdAndUser_userId(titleId, userId);
		return checkTitleUsedList;
	}

	private WishList setProperties(User existingUser, Title existingTitle, WishList wishList) {
		wishList.setTitle(existingTitle);
		wishList.setUser(existingUser);
		return wishList;
	}

	@Override
	public List<WishList> findUserWishList(String userId) {
		User existingUser = userRepository.findOne(userId);
		if (existingUser == null) {
			throw new UserNotFound("No User found with the given ID");
		}
		List<WishList> userWishList = wishListRepository.findByUser_userId(userId);
		return userWishList;
	}

}

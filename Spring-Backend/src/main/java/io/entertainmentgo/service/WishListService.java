package io.entertainmentgo.service;

import java.util.List;

import io.entertainmentgo.entity.WishList;

public interface WishListService {

	WishList addTitleToWishList(WishList wishList);

	List<WishList> findUserWishList(String userId);

}

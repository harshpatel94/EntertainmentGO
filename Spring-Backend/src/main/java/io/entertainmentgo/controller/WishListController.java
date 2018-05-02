package io.entertainmentgo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.entertainmentgo.entity.WishList;
import io.entertainmentgo.service.WishListService;

@RestController
@RequestMapping(path="/wishlists",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WishListController {
	
	@Autowired
	private WishListService wishListService;
	
	@RequestMapping(method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public WishList addTitleToWishList(@RequestBody WishList wishList){
		WishList persistedWishList = wishListService.addTitleToWishList(wishList);
		return persistedWishList;
	}
	
	@RequestMapping(path="/{userId}",method=RequestMethod.GET,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<WishList> findUserWishList(@PathVariable(name="userId") String userId){
		List<WishList> userWishList = wishListService.findUserWishList(userId);
		return userWishList;
	}
	
	

}

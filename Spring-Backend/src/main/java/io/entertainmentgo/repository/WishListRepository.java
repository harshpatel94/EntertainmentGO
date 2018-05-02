package io.entertainmentgo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import io.entertainmentgo.entity.WishList;

public interface WishListRepository extends CrudRepository<WishList,String>{

	List<WishList> findByTitle_titleIdAndUser_userId(String titleId, String userId);

	List<WishList> findByUser_userId(String userId);

	List<WishList> findByTitle_titleId(String titleId);

}

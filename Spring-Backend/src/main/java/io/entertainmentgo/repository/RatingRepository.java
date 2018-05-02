package io.entertainmentgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import io.entertainmentgo.entity.Rating;

public interface RatingRepository extends CrudRepository<Rating, String>{

	@Query("SELECT AVG(r.rating) FROM Rating r where r.title.titleId = :titleId")
	Double findAvgByTitle(@Param(value="titleId") String titleId);

	List<Rating> findByTitle_titleIdAndUser_userId(String titleId, String userId);

	List<Rating> findByTitle_titleId(String titleId);

}

package io.entertainmentgo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import io.entertainmentgo.entity.Title;

public interface TitleRepository extends CrudRepository<Title, String> {

	List<Title> findByImdbRatingGreaterThanOrderByImdbRatingDesc(Double toprating);

	@Query("SELECT t FROM Title t WHERE t.type = :type and t.imdbRating > :toprating ORDER BY t.imdbRating DESC")
	List<Title> findByImdbRatingAndTypeGreaterThanOrderByImdbRatingDesc(@Param(value = "toprating") double toprating,@Param(value = "type") String type);

	List<Title> findByType(String value);

	List<Title> findByGenre_genre(String value);

	List<Title> findByYear(int value);

	List<Title> findByOrderByImdbRatingDesc();

	List<Title> findByOrderByImdbVotesDesc();

	List<Title> findByOrderByYearDesc();

	Title findByImdbID(String imdbID);


}

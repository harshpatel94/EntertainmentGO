package io.entertainmentgo.service;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.entertainmentgo.entity.Comment;
import io.entertainmentgo.entity.Genre;
import io.entertainmentgo.entity.Rating;
import io.entertainmentgo.entity.Title;
import io.entertainmentgo.entity.WishList;
import io.entertainmentgo.exception.NoTitleFoundException;
import io.entertainmentgo.exception.TitleAlreadyExists;
import io.entertainmentgo.exception.UnknownFilterException;
import io.entertainmentgo.exception.UnknownSortException;
import io.entertainmentgo.repository.CommentRepository;
import io.entertainmentgo.repository.GenreRepository;
import io.entertainmentgo.repository.RatingRepository;
import io.entertainmentgo.repository.TitleRepository;
import io.entertainmentgo.repository.WishListRepository;

@Service
@Transactional
public class TitleServiceImpl implements TitleService {

	@Autowired
	private TitleRepository titleRepository;

	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private WishListRepository wishListRepository;

	@Override
	public List<Title> findAllTitles() {
		List<Title> allTitles = (List<Title>) titleRepository.findAll();
		return allTitles;
	}

	@Override
	public List<Title> findTopTitles(Double toprating) {
		List<Title> topTitles = titleRepository.findByImdbRatingGreaterThanOrderByImdbRatingDesc(toprating);
		return topTitles;
	}

	@Override
	public List<Title> findTopTitles(double toprating, String type) {
		List<Title> topTitles = titleRepository.findByImdbRatingAndTypeGreaterThanOrderByImdbRatingDesc(toprating,
				type);
		return topTitles;
	}

	@Override
	public List<Title> findByType(String basedOn, String value) throws UnknownFilterException {
		List<Title> typeTitles = null;
		if (basedOn.equals("type")) {
			typeTitles = titleRepository.findByType(value);
			return typeTitles;
		} else if (basedOn.equals("genre")) {
			typeTitles = titleRepository.findByGenre_genre(value);
			return typeTitles;
		} else if (basedOn.equals("year")) {
			typeTitles = titleRepository.findByYear(Integer.parseInt(value));
			return typeTitles;
		} else {
			throw new UnknownFilterException("No such Filter Found");
		}

	}

	@Override
	public List<Title> findBySort(String sortBy) throws UnknownSortException {
		List<Title> sortTitles = null;

		if (sortBy.equals("imdbrating")) {
			sortTitles = titleRepository.findByOrderByImdbRatingDesc();
			return sortTitles;

		} else if (sortBy.equals("imdbvote")) {
			sortTitles = titleRepository.findByOrderByImdbVotesDesc();
			return sortTitles;
		} else if (sortBy.equals("year")) {
			sortTitles = titleRepository.findByOrderByYearDesc();
			return sortTitles;
		} else {
			throw new UnknownSortException("No such sort field Found");
		}

	}

	@Override
	public Title findTitle(String titleId) throws NoTitleFoundException {
		Title existingTitle = titleRepository.findOne(titleId);
		if (existingTitle == null) {
			throw new NoTitleFoundException("No title found with the given ID");
		} else {
			return existingTitle;
		}

	}

	@Override
	public Title addTitle(Title newTile) {
		
		Title existingTitle = titleRepository.findByImdbID(newTile.getImdbID());
		if (existingTitle != null) {
			throw new TitleAlreadyExists("Title exists with the ImdbId " + newTile.getImdbID());
		}
		List<Genre> genreList = newTile.getGenre();
		if(!genreList.isEmpty()){
			List<Genre> persistedGenreList = addTitleGeners(genreList);
			newTile.setGenre(persistedGenreList);
		}		
		Title persistedTile = titleRepository.save(newTile);
		return persistedTile;
	}

	private List<Genre> addTitleGeners(List<Genre> genreList) {
		List<Genre> persistedGenreList = new LinkedList<Genre>();
		for (Genre genre : genreList) {
			Genre existingGenre = null;
			existingGenre = genreRepository.findByGenre(genre.getGenre());
			System.out.println(existingGenre);
			if (existingGenre == null) {
				genre.setGenreId(null);
				existingGenre = genreRepository.save(genre);
			}
			persistedGenreList.add(existingGenre);
		}
		return persistedGenreList;
	}

	@Override
	public Title updateTitle(Title updateTitle) {
		List<Genre> genreList = updateTitle.getGenre();
		if(!genreList.isEmpty()){
			List<Genre> persistedGenreList = addTitleGeners(genreList);
			updateTitle.setGenre(persistedGenreList);
		}
		Title ModifiedTitle = titleRepository.save(updateTitle);
		return ModifiedTitle;
	}

	@Override
	public void deleteTitle(String titleId) {
		List<Rating> titleRatings = ratingRepository.findByTitle_titleId(titleId);
		List<Comment> titleComments = commentRepository.findByTitle_titleId(titleId);
		List<WishList> titleWishLists = wishListRepository.findByTitle_titleId(titleId);
		if(!titleRatings.isEmpty()){
			removeTitleRatings(titleRatings);	
		}
		if(!titleComments.isEmpty()){
			removeTitleComments(titleComments);
		}
		if(!titleWishLists.isEmpty()){
			removeTitleWishList(titleWishLists);
		}
		Title existingTitle = titleRepository.findOne(titleId);
		titleRepository.delete(existingTitle);
	}

	private void removeTitleWishList(List<WishList> titleWishLists) {
		for (WishList wishList : titleWishLists) {
			wishListRepository.delete(wishList);
		}
		
	}

	private void removeTitleComments(List<Comment> titleComments) {
		for (Comment comment : titleComments) {
			commentRepository.delete(comment);
		}		
	}

	private void removeTitleRatings(List<Rating> titleRatingsList) {
		for (Rating rating : titleRatingsList) {
			ratingRepository.delete(rating);
		}		
	}

}

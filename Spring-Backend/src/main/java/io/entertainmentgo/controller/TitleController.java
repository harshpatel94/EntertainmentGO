package io.entertainmentgo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.entertainmentgo.entity.Title;
import io.entertainmentgo.exception.NoTitleFoundException;
import io.entertainmentgo.exception.UnknownFilterException;
import io.entertainmentgo.exception.UnknownSortException;
import io.entertainmentgo.service.TitleService;

@RestController
@RequestMapping(path = "/titles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TitleController {

	@Autowired
	private TitleService titleService;

	// Fetch all the titles present in the database
	@RequestMapping(method = RequestMethod.GET)
	public List<Title> findAllTitles() {
		List<Title> allTitles = titleService.findAllTitles();
		return allTitles;
	}
	// View more details about the title like Director, Actors, Plot, IMDB Rating etc, so that user can get familiar with it.
	
	@RequestMapping(path="/{titleId}",method=RequestMethod.GET)
	public Title findTitle(@PathVariable(name="titleId") String titleId) throws NoTitleFoundException{
		Title existingTitle = titleService.findTitle(titleId); 
		return existingTitle;
	}

	// Check TOP RATED MOVIES and TOP RATED TV SERIES (as per IMDB Rating) so
	// that user can see which titles are most rated by IMDB users.
	@RequestMapping(path = "/topTiles", method = RequestMethod.GET)
	public List<Title> findTopTitles() {
		List<Title> topTitles = titleService.findTopTitles(9.0);
		return topTitles;
	}

	// Applying Filteration with type .Check TOP RATED MOVIES and TOP RATED TV
	// SERIES (as per IMDB Rating) so that user can see which titles are most
	// rated by IMDB users.
	@RequestMapping(path = "/topTiles/{type}", method = RequestMethod.GET)
	public List<Title> findTopTitles(@PathVariable(name = "type") String type) {
		List<Title> topTitles = titleService.findTopTitles(9.0, type);
		return topTitles;
	}

	// Filter the catalog based on type,year,genre fields

	@RequestMapping(path = "filter/{basedOn}/{value}", method = RequestMethod.GET)
	public List<Title> filterTitle(@PathVariable(name = "basedOn") String basedOn,
			@PathVariable(name = "value") String value) throws UnknownFilterException {
		List<Title> typeTitles = titleService.findByType(basedOn, value);
		return typeTitles;
	}

	// Sorting the catalog by IMDB Ratings, Year, IMDB Votes so that user can
	// see them in an order.
	@RequestMapping(path = "sort/{sortBy}", method = RequestMethod.GET)
	public List<Title> sortTitle(@PathVariable(name = "sortBy") String sortBy) throws UnknownSortException {
		List<Title> sortTitles = titleService.findBySort(sortBy);
		return sortTitles;
	}
	// 
	@RequestMapping(method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Title addTitle(@RequestBody Title newTile){
		Title persistedTitle = titleService.addTitle(newTile);
		return persistedTitle;
	}
	
	@RequestMapping(method=RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Title updateTitle(@RequestBody Title updateTitle){
		Title modifiedTitle = titleService.updateTitle(updateTitle);
		return modifiedTitle;
	}
	
	@RequestMapping(path="/{titleId}",method=RequestMethod.DELETE)
	public void deleteTitle(@PathVariable(name="titleId") String titleId){
		titleService.deleteTitle(titleId);
	}

}

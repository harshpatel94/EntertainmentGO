package io.entertainmentgo.service;

import java.util.List;

import io.entertainmentgo.entity.Title;
import io.entertainmentgo.exception.NoTitleFoundException;
import io.entertainmentgo.exception.UnknownFilterException;
import io.entertainmentgo.exception.UnknownSortException;

public interface TitleService {

	public List<Title> findAllTitles();

	public List<Title> findTopTitles(Double d);

	public List<Title> findTopTitles(double d, String type);

	public List<Title> findByType(String basedOn, String value) throws UnknownFilterException;

	public List<Title> findBySort(String sortBy) throws UnknownSortException;

	public Title findTitle(String titleId) throws NoTitleFoundException;

	public Title addTitle(Title newTile);

	public Title updateTitle(Title updateTitle);

	public void deleteTitle(String titleId);

}

package io.entertainmentgo.repository;

import org.springframework.data.repository.CrudRepository;

import io.entertainmentgo.entity.Genre;

public interface GenreRepository extends CrudRepository<Genre, String> {

	Genre findByGenre(String genre);

}

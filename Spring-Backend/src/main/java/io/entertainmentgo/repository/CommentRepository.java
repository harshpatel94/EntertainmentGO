package io.entertainmentgo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.entertainmentgo.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment,String >{

	List<Comment> findByTitle_titleId(String titleId);

}

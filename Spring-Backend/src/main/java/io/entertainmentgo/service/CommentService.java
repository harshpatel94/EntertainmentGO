package io.entertainmentgo.service;

import java.util.List;

import io.entertainmentgo.entity.Comment;

public interface CommentService {

	public Comment addComment(Comment comment);

	public List<Comment> findAllByTitle(String titleId);

}

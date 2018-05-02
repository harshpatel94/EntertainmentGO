package io.entertainmentgo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.entertainmentgo.entity.Comment;
import io.entertainmentgo.entity.Title;
import io.entertainmentgo.entity.User;
import io.entertainmentgo.exception.NoTitleFoundException;
import io.entertainmentgo.exception.UserNotFound;
import io.entertainmentgo.repository.CommentRepository;
import io.entertainmentgo.repository.TitleRepository;
import io.entertainmentgo.repository.UserRepository;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private TitleRepository titleRepository;
	
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public Comment addComment(Comment comment) {
		User existingUser = userRepository.findOne(comment.getUser().getUserId());
		Title existingTitle = titleRepository.findOne(comment.getTitle().getTitleId());
		if (existingTitle == null) {
			throw new NoTitleFoundException("No title found with the given ID");
		}
		if (existingUser == null) {
			throw new UserNotFound("No User found with the given ID");
		}
		Comment settedComment = setProperties(existingUser,existingTitle,comment); 
		Comment persistedComment = commentRepository.save(settedComment);
		return persistedComment;
	}


	private Comment setProperties(User existingUser, Title existingTitle, Comment comment) {
		
		comment.setTitle(existingTitle);
		comment.setUser(existingUser);
		return comment;
	}


	@Override
	public List<Comment> findAllByTitle(String titleId) {
		Title existingTitle = titleRepository.findOne(titleId);
		if (existingTitle == null) {
			throw new NoTitleFoundException("No title found with the given ID");
		}
		List<Comment> titleComments = commentRepository.findByTitle_titleId(titleId);
		return titleComments;
	}


	

}

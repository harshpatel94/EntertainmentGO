package io.entertainmentgo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.entertainmentgo.entity.Comment;
import io.entertainmentgo.service.CommentService;

@RestController
@RequestMapping(path="/comments",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Comment addComment(@RequestBody Comment comment){
		Comment persistedComment = commentService.addComment(comment);
		return persistedComment;
	}
	
	@RequestMapping(path="/{titleId}",method=RequestMethod.GET)
	public List<Comment> findAllByTitle(@PathVariable(name="titleId") String titleId){
		List<Comment> titleComments = commentService.findAllByTitle(titleId);
		return titleComments;
	}
	
	

}

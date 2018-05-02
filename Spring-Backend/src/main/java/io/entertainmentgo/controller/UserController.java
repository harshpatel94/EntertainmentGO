package io.entertainmentgo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.entertainmentgo.entity.User;
import io.entertainmentgo.exception.UserAlreadyExists;
import io.entertainmentgo.service.UserService;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public List<User> findAll() {
		List<User> usersList = userService.findAll();
		return usersList;
	}
	@RequestMapping(path="/register",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public User register(@RequestBody User newUser) throws UserAlreadyExists{
		User addedUser = userService.createUser(newUser);
		return addedUser;
	}
	@RequestMapping(path="/login",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public User login(@RequestBody User loginUser){
		User existingUser = userService.loginUser(loginUser);
		return existingUser;
	}
	
	@RequestMapping(path="{userId}",method=RequestMethod.GET,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public User findOne(@PathVariable(name="userId") String userId){
		User existingUser = userService.findOne(userId);
		return existingUser;
	}
	
}

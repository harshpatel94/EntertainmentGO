package io.entertainmentgo.service;

import java.util.List;

import io.entertainmentgo.entity.User;
import io.entertainmentgo.exception.UserAlreadyExists;

public interface UserService {

	public List<User> findAll();

	public User createUser(User newUser) throws UserAlreadyExists;

	public User loginUser(User loginUser);

	public User findOne(String userId);

}

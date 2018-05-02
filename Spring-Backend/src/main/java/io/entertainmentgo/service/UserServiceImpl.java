package io.entertainmentgo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.entertainmentgo.entity.User;
import io.entertainmentgo.exception.UserAlreadyExists;
import io.entertainmentgo.exception.UserNotFound;
import io.entertainmentgo.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> findAll() {
		List<User> usersList = (List<User>) userRepository.findAll();
		return usersList;
	}

	@Override
	public User createUser(User newUser) throws UserAlreadyExists {
		// Get User details from DataBase
		User existingUser = userRepository.findByEmailId(newUser.getEmailId());
		// Check if User already exists with the email. If exists throw an exception
		if(existingUser != null){
			throw new UserAlreadyExists("User exits with the Email given");
		}else{
			newUser.setRole("user");
			User addedUser  = userRepository.save(newUser);
			return addedUser;
		}
	}

	@Override
	public User loginUser(User loginUser) {
		
		User existingUser = userRepository.findByEmailIdAndPassword(loginUser.getEmailId(),loginUser.getPassword());
		if(existingUser != null){
			return existingUser;	
		}else{
			throw new UserNotFound("No User Found");
		}
		
	}

	@Override
	public User findOne(String userId) {
		User existingUser = userRepository.findOne(userId);
		return existingUser;
	}

}

package io.entertainmentgo.repository;

import org.springframework.data.repository.CrudRepository;

import io.entertainmentgo.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

	User findByEmailId(String string);

	User findByEmailIdAndPassword(String emailId, String password);



}

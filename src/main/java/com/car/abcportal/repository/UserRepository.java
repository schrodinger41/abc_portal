package com.car.abcportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.abcportal.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByUserName(String name);
	
}

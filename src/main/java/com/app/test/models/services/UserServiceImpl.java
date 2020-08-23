package com.app.test.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.test.models.entity.User;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserService userService;
	
	@Override
	public User findById(Long id) {
		return userService.findById(id);
	}

}

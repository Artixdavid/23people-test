package com.app.test.models.dao;

import org.springframework.data.repository.CrudRepository;
import com.app.test.models.entity.User;


public interface IUserDao extends CrudRepository<User, Long>{
	
	

}

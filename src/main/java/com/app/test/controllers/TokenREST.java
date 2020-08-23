package com.app.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.test.dto.TokenResponse;
import com.app.test.models.dao.IUserDao;
import com.app.test.models.entity.User;
import com.app.test.models.services.ITokenService;

@CrossOrigin(origins = { "http://localhost:4200" }, methods = { RequestMethod.GET })
@RestController
public class TokenREST {

	@Autowired
	private ITokenService tokenService;

	@Autowired
	private IUserDao userService;

	@GetMapping("/token")
	public ResponseEntity<?> getStudens() {
		User user = userService.findById(1L).orElse(null);
		TokenResponse response = tokenService.getToken(user);
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}
}

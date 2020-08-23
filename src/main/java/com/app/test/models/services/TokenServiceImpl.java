package com.app.test.models.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.test.auth.service.JWTService;
import com.app.test.dto.TokenResponse;
import com.app.test.models.entity.User;

@Service
public class TokenServiceImpl implements ITokenService {

	@Autowired
	private JWTService jWTService;

	@Override
	public TokenResponse getToken(User user) {
		try {
			String token = jWTService.create(user);
			TokenResponse response = new TokenResponse();
			response.setToken(token);
			return response;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

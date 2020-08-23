package com.app.test.models.services;

import com.app.test.dto.TokenResponse;
import com.app.test.models.entity.User;

public interface ITokenService {

	public TokenResponse getToken(User user);

}

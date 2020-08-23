package com.app.test.auth.service;

import java.io.IOException;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

import com.app.test.models.entity.User;

import io.jsonwebtoken.Claims;

public interface JWTService {

	public String create(User user) throws IOException;

	public User validate(String token);

	public Claims getClaims(String token);

	public String getUsername(String token);

	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;

	public String resolve(String token);

}

package com.app.test.auth.service;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.app.test.Utils.Utils;
import com.app.test.models.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JWTServiceImpl implements JWTService {

	@Override
	public String create(User user) throws JsonProcessingException {

		Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
		Date expirationDate = Date.from(expirationTime);

		String token = Jwts.builder().claim("id", user.getId()).claim("sub", user.getUserName())
				.claim("admin", user.isAdmin()).setExpiration(expirationDate)
				.signWith(Utils.SECRETKEY, SignatureAlgorithm.HS256).compact();
		return token;
	}

	@Override
	public User validate(String token) {

		Jws<Claims> jwsClaims;
		try {
			jwsClaims = Jwts.parserBuilder().setSigningKey(Utils.SECRETKEY).build().parseClaimsJws(token);
			String username = jwsClaims.getBody().getSubject();
			Integer userId = jwsClaims.getBody().get("id", Integer.class);
			boolean isAdmin = jwsClaims.getBody().get("admin", Boolean.class);
			User user = new User();
			user.setId(new Long(userId));
			user.setAdmin(isAdmin);
			user.setUserName(username);

			return user;
		} catch (SignatureException ex) {

		}

		return null;

	}

	@Override
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	@Override
	public String resolve(String token) {

		if (token != null && token.startsWith(Utils.PREFIXTOKEN)) {
			return token.replace(Utils.PREFIXTOKEN, "");
		}

		return null;
	}

	@Override
	public Claims getClaims(String token) {
		Claims claimsToken = Jwts.parserBuilder().setSigningKey(Utils.SECRETKEY).build().parseClaimsJws(token)
				.getBody();
		return claimsToken;
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}

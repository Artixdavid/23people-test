package com.app.test.auth.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.test.auth.service.JWTService;
import com.app.test.models.entity.User;


public class JWTAuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	private com.app.test.auth.service.JWTService jWTService;
	
	public JWTAuthorizationFilter(JWTService jwtService) {
		this.jWTService = jwtService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String authorizationHeader = request.getHeader("Authorization");
		

        if (authorizationHeaderIsInvalid(authorizationHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken token = createToken(authorizationHeader);

        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
		
	}

	private boolean authorizationHeaderIsInvalid(String authorizationHeader) {
        return authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ");
    }

    private UsernamePasswordAuthenticationToken createToken(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        User userPrincipal = jWTService.validate(token);
        
        if(userPrincipal == null) {
        	return null;
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (userPrincipal.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
    }
	
}

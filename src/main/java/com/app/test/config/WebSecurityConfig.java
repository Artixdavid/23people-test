package com.app.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.test.auth.filter.JWTAuthorizationFilter;
import com.app.test.auth.service.JWTService;

@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JWTService jwtService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().antMatchers("/**").permitAll().antMatchers("/v2/**","/swagger-ui.html","/swagger-ui.html**").permitAll().anyRequest().authenticated();
	}
}

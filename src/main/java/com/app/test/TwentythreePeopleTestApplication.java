package com.app.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.test.auth.filter.JWTAuthorizationFilter;
import com.app.test.auth.service.JWTService;

@SpringBootApplication
public class TwentythreePeopleTestApplication {

	@Autowired
	private JWTService jwtService;
	
	public static void main(String[] args) {
		SpringApplication.run(TwentythreePeopleTestApplication.class, args);
	}
	
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/token").permitAll()
				.anyRequest().authenticated();
		}
	}

}

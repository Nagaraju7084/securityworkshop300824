package com.medilab.preclinic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/").permitAll() //this will allow all urls to access meaning without security
		.antMatchers("/dashboard").authenticated() //should be authenticate
		.antMatchers("/doctor").authenticated() //should be authenticate
		.and()
		.formLogin() //either through form login
		.and()
		.httpBasic(); //or through httpbasic means via postman headers
	}
}


//beans we can define with @Bean, those beans we can define in the class which annotated with @Configuration
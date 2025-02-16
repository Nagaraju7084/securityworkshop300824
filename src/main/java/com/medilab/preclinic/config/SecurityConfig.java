package com.medilab.preclinic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;

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
	
	//instead of default generated user details such as user and generated password
	//define our custom user details may be in memory user details manager or jdbc user details manager, etc
	//using AuthenticationManagerBuilder
	//AuthenticationManagerBuilder will build the AuthenticationManager
	//AuthenticationManager will be implemented by the AuthenticationProvider 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication() //this inmemoryuserdetailsmanager take all these users and calling the userdetails => createuser
		//inMemoryAuthentication() => InMemoryUserDetailsManagerConfigurer => InMemoryUserDetailsManager => createUser
		//createUser(UserDetails user) = given user details
		//finally the users are added into the user map
		.withUser("admin").password("admin").authorities("admin") //our own user store using in memory
		.and()
		.withUser("user").password("user").authorities("user");
		//if we run the application, we will get the exception as : java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
		//because AuthenticationProvider will verify the password what we are enter with the password present in UserDetailsService using PasswordEncoder
		//this PasswordEncoder used by the AuthenticationProvider
		//that is the reason, while authenticating, AuthenticationProvider have not found any
		//PasswordEncoder, and hence we got the PasswordEncoder error here
	}
}

//beans we can define with @Bean, those beans we can define in the class which annotated with @Configuration
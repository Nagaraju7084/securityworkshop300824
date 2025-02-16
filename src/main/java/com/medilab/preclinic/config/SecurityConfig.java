package com.medilab.preclinic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception { //configure(HttpSecurity http) is used to define the urls to be protected
		//and define the authentication methods and also to define the various application artifacts such as cors to be enable
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
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { //is used to build mostly for the custom authenticationprovider and custom userdetailsmanager, inmemoryuserdetailsmanager
		//AuthenticationManagerBuilder is used to build the AuthenticationManager, that AuthenticationManager will provides the Authentication details to the AuthenticationProvider
		//AuthenticationManagerBuilder is also used to define the UserDetailsManager to create the users schema
		auth.inMemoryAuthentication() //this inmemoryuserdetailsmanager take all these users and calling the userdetails => createuser
		//inMemoryAuthentication() => InMemoryUserDetailsManagerConfigurer => InMemoryUserDetailsManager => createUser
		//createUser(UserDetails user) = given user details
		//finally the users are added into the user map
		.withUser("admin").password("admin").authorities("admin") //our own user store using in memory
		.and()
		.withUser("user").password("user").authorities("user")
		//if we run the application, we will get the exception as : java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
		//because AuthenticationProvider will verify the password what we are enter with the password present in UserDetailsService using PasswordEncoder
		//this PasswordEncoder used by the AuthenticationProvider
		//that is the reason, while authenticating, AuthenticationProvider have not found any
		//PasswordEncoder, and hence we got the PasswordEncoder error here
		//configure PasswordEncoder
		.and()
		.passwordEncoder(NoOpPasswordEncoder.getInstance()); // not recommended to use
		//NoOpPasswordEncoder use to not to encode the passwords what we will enter, keep as is
		//while storing the users into inmemory db
	}

	@Override
	public void configure(WebSecurity web) throws Exception { //to static resources should have to be unsecure
		web.ignoring().antMatchers("/assets/**"); //this configure(WebSecurity web) is mostly used for ignoring the security check on the static resources
	}
}

//beans we can define with @Bean, those beans we can define in the class which annotated with @Configuration
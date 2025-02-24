package com.medilab.preclinic.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@SuppressWarnings("deprecation")
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider; //configuring the our customauthenticationprovider class
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { 
		http.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/dashboard").authenticated()
		.antMatchers("/doctor").authenticated()
		.and()
		.formLogin()
		.and()
		.httpBasic();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider); //authenticationmanagerbuilder will build the authenticationmanager
		//that authenticationmanager delegate the given request to the conigured authentication provider i.e. our customAuthenticationProvider
		//daoauthenticationprovider will not execute rather, our customAuthenticationProvider will execute
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(5);
		//internally it will do the entered rawpassword will by encode and will check
		//encodedpassword with the existing databse encoded password
		//rawpassword => encodedpassword == databasepassword(already encoded)
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/assets/**");
	}

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder(5).encode("test"));
		
		//user = james@gmail.com
		//password = test
	}
}
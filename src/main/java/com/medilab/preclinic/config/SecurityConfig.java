package com.medilab.preclinic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@SuppressWarnings("deprecation")
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
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
		/*auth.inMemoryAuthentication()
		.withUser("admin").password("admin").authorities("admin")
		.and()
		.withUser("user").password("user").authorities("user");*/

		//jdbcauthentication
		//if we configure the AuthenticationManagerBuilder with the jdbcAuthentication, that jdbcAuthenticationmanager internally will use the
		//userdetails as the JdbcUserDetailsManager
		//AuthenticationManager will use jdbcauthenticationprovider, jdbcauthenticationprovider will use this JdbcUserDetailsManager
		//JdbcUserDetailsManager will excepting the users and authorities tables
		//the below boolean values for the users table's enable column values
		// 0 - disable
		// 1 - enable
		// /resources/sql/scripts.sql => run this scripts in sql editor of the mysql work bench
		//so, required tables were created i.e. users table and authorities table which are expected by the JdbcUserDetailsManager
		auth.jdbcAuthentication().dataSource(dataSource);
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return NoOpPasswordEncoder.getInstance();
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/assets/**");
	}
}
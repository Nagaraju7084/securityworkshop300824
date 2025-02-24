package com.medilab.preclinic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		System.out.println("loggedin username is:\t" + userName);
		Authentication authenticationResponse = null;
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
		if(userDetails != null) {
			boolean isPasswordMatched = passwordEncoder.matches(String.valueOf(authentication.getCredentials()), userDetails.getPassword());
			System.out.println("does authentication success?:\t" + isPasswordMatched);
			if(isPasswordMatched) {
				authenticationResponse = new UsernamePasswordAuthenticationToken(userName, null, userDetails.getAuthorities());
			}
		}
		return authenticationResponse;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}

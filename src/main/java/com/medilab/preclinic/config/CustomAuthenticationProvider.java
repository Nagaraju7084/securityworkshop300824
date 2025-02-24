package com.medilab.preclinic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component //by using this annotation, ioc will auto detect the class and inject the object
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder; //we exposed as bean in the securityconfig class
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		System.out.println("loggedin username is:\t" + userName);
		Authentication authenticationResponse = null; //authentication is an interface, usernameandpassworduathenticationtoken is the implementation class
		//whatever the user we entered in the form/ui form that user will be loaded here by using loaduserbyusername method
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
		//prepare authentication object this decision not only consider only user but also
		//consider password coming from the database as well
		if(userDetails != null) { //we have to verify the user entered password with the password coming from the userdetails(database password)
			boolean isPasswordMatched = passwordEncoder.matches(String.valueOf(authentication.getCredentials()), userDetails.getPassword());
			System.out.println("does authentication success?:\t" + isPasswordMatched);
			if(isPasswordMatched) { //if password is matching i.e. user entered password == password coming from the userdetails(database password)
				//then prepare the authentication object as response object with the authorities
				//authentication is an interface, usernameandpassworduathenticationtoken is the implementation class so prepare it
				authenticationResponse = new UsernamePasswordAuthenticationToken(userName, null, userDetails.getAuthorities());
			}
		}
		return authenticationResponse;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true; //we can make it as true by default because our customauthenticatprovider supports the authentication so true
		//if we make it as false, the authenticate method willn't execute for sure
		//and also using this authentication parameter we can know what type of authentication it is
	}

}

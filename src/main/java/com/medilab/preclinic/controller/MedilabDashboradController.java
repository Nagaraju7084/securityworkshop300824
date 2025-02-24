package com.medilab.preclinic.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class MedilabDashboradController {

	@RequestMapping
	public String viewMedilabDashborad() {
		System.out.println("i am in dashboard");
		Authentication authenticationResponse = SecurityContextHolder.getContext().getAuthentication();
		//UserDetails userDetails = authenticationResponse.getPrincipal();
		System.out.println("============logged in user details========");
		System.out.println("username :\t"+authenticationResponse.getPrincipal()); //principal = username
		System.out.println("password :\t"+authenticationResponse.getCredentials()); //credentails = password
		authenticationResponse.getAuthorities().stream().forEach(authority ->{
			System.out.println("authority :\t"+authority);
		});
		
		//find the ip address of logged in user
		WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authenticationResponse.getDetails();
		String remoteAddress = webAuthenticationDetails.getRemoteAddress();
		System.out.println("user logged in from machine:\t"+remoteAddress);
		
		return "dashboard";
	}
}

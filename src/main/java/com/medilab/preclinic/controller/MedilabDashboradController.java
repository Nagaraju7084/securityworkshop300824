package com.medilab.preclinic.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
		Authentication authnResponse = SecurityContextHolder.getContext().getAuthentication();
		//UserDetails userDetails = (UserDetails) authnResponse.getPrincipal();
		System.out.println("=============================");
		System.out.println("logged in user details are:\t");
		System.out.println("user name:\t"+authnResponse.getPrincipal());
		System.out.println("password is:\t"+authnResponse.getCredentials());
		System.out.println("authorities are:\t");
		for(GrantedAuthority ga : authnResponse.getAuthorities()) {
			System.out.println(ga.getAuthority());
		}
		
		WebAuthenticationDetails webAuthDetails = (WebAuthenticationDetails) authnResponse.getDetails();
		String remoteAddress = webAuthDetails.getRemoteAddress();
		System.out.println("user logged in from machine:\t"+remoteAddress);
		
		System.out.println("=============================");
		return "dashboard";
	}
}

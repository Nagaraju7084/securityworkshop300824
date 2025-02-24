package com.medilab.preclinic.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.medilab.preclinic.model.MedilabUser;
import com.medilab.preclinic.model.UserRole;
import com.medilab.preclinic.repo.MediUserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MediUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MedilabUser databaseUser = userRepository.findUserByEmail(username);
		//lets prepare userdetails but userdetails is an interface so use implementation class i.e. user
		//so prepare user
		List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();
		UserRole userRole = databaseUser.getRole();
		authoritiesList.add(new SimpleGrantedAuthority(userRole.getName()));
		
		return new User(databaseUser.getEmail(), databaseUser.getPassword(), authoritiesList); //here user is the implementation class of userdetails
	}

}


//we know the that authenticationprovider delegates the responsibility
//of finding the user in the system to userdetailservice
//so create customuserdetailsservice which will go to our database and get the data
//so, our customuserdetailsservice class should implements userdetailsservice so that
//a loaduserbyusername method will be available to our class
package com.school.sbm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.school.sbm.repository.IUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	private IUserRepository iUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return iUserRepository.findByUsername(username)
				.map(user->
				new CustomUserDetails(user))
				.orElseThrow(()->new UsernameNotFoundException("username not found"));

	}

}

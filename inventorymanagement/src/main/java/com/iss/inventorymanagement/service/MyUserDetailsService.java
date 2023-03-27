package com.iss.inventorymanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iss.inventorymanagement.exception.UserException;
import com.iss.inventorymanagement.model.request.AuthRequest;
import com.iss.inventorymanagement.model.response.ProductResponseModel;
import com.iss.inventorymanagement.model.response.ProfileResponseModel;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		List<AuthRequest> user = jdbc.query("SELECT username,password,isAdmin from users where username=?",
				BeanPropertyRowMapper.newInstance(AuthRequest.class), userName);
//		List<Boolean> isAdmin = new ArrayList<>();
//		isAdmin.add(user.get(0).isAdmin());
		if(user.size()==0) {
			throw new UserException("User with username"+userName+" Doesnot Exists");
		}
//		user.get(0);
		return new User(user.get(0).getUsername(), user.get(0).getPassword(), new ArrayList<>());
	}
	
	public int isAdmin(String userName) {
		AuthRequest profile = jdbc.queryForObject("SELECT username,password,isAdmin from users where username=?",
				BeanPropertyRowMapper.newInstance(AuthRequest.class), userName);
		System.out.println(profile);
		return profile.getIsadmin();

	}

}

package com.iss.inventorymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.iss.inventorymanagement.jwt.JwtUtil;
import com.iss.inventorymanagement.model.request.AuthRequest;
import com.iss.inventorymanagement.model.request.ProfileRequestModel;
import com.iss.inventorymanagement.model.response.AuthResponse;
import com.iss.inventorymanagement.model.response.ProfileResponseModel;
import com.iss.inventorymanagement.service.MyUserDetailsService;

/*
 * This Class Controls the endpoints for getting the actions required by the users
*/

@CrossOrigin(originPatterns = "*")
@RestController
public class UserController {
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtTokenUtil;

	/*
	 * *********NOT Implemented************
	 * Endpoint to accept the user input and register him
	*/
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody ProfileRequestModel authRequest) {
		
		
		return new ResponseEntity<>("User Registered",HttpStatus.OK);
	}
	
	@PostMapping("/auth")
	public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest) throws Exception {
		/*
		 * Endpint th eaccept authenticate and return JWT for the succesfull authenticated user
		 * Else throw the Userexception of Incorrect username or password
		*/
		
		try {
			//Authenticate the user Checks if the provided credentials match with the details in the database
			// Else Throw bad credential error
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		//if user is successfully athenticated then generate the JWT
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthResponse(jwt));
	}
	
	@GetMapping("/profile")
	public ResponseEntity<ProfileResponseModel> getUserProfile(@RequestHeader("Authorization") String jwt){
		/*
		 * Returns the user details from the database using the jwt saved
		 * Accepts the JWT token with the Bearer heading
		*/
		String username = jwtTokenUtil.extractUsername(jwt.substring(7));
		List<ProfileResponseModel> userDetails = jdbc.query("SELECT * from users where username=?",
				BeanPropertyRowMapper.newInstance(ProfileResponseModel.class),username);
		return new ResponseEntity<>(userDetails.get(0), HttpStatus.OK);
	}

}

package com.sunbasedata.cms.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunbasedata.cms.pojo.JwtRequest;
import com.sunbasedata.cms.pojo.JwtResponse;
import com.sunbasedata.cms.security.JwtHelper;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {


	private Logger logger = org.slf4j.LoggerFactory.getLogger(AuthController.class);
	
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private JwtHelper helper;

    	// post mapping to generate token for valid user

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
    	// method validate user if not varify, wil throw exception
    	
    	 
        logger.info("auth controller excecute ");

        this.doAuthenticate(request.getUsername(), request.getPassword());

       
        // load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
       
        // generate token 
        String token = this.helper.generateToken(userDetails);
        JwtResponse response = JwtResponse.builder().token(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    // authenticate the user 
    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }
       

} 
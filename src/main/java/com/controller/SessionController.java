package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.UserBean;
//public api accesss by all without any token accesss
import com.dao.UserDao;
import com.dto.LoginDto;
import com.service.TokenService;

@RestController
public class SessionController {

	@Autowired
	UserDao userdao;

	@Autowired
	TokenService token;

	@Autowired
	BCryptPasswordEncoder bcrypt;

	@PostMapping("/signup")
	public ResponseEntity signup(@RequestBody UserBean user) {

		UserBean dbuser = userdao.getUserByEmail(user.getEmail());
		if (dbuser != null) {
			return ResponseEntity.unprocessableEntity().body(user);
		} else {
			System.out.println(user.getEmail());
			String encodedPasword = bcrypt.encode(user.getPassword());
			user.setPassword(encodedPasword);
			userdao.createUser(user);
			return ResponseEntity.ok(user);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto logindto) {
		UserBean user = userdao.getUserByEmail(logindto.getEmail());
		boolean isInvalid = false;
		if (user == null) {
			isInvalid = true;
		} else if (bcrypt.matches(logindto.getPassword(), user.getPassword()) == false) {
			isInvalid = true;
		}

		if (isInvalid == true) {
			return ResponseEntity.unprocessableEntity().body(logindto);
		} else {
			user.setAuthToken(token.generateToken(20));
			userdao.updateToken(user.getEmail(), user.getAuthToken());
			return ResponseEntity.ok(user);
		}

	}

}

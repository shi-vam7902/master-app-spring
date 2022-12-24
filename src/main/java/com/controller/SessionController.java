package com.controller;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.UserBean;
//public api accesss by all without any token accesss
import com.dao.UserDao;
import com.dto.ForgetPasswordDto;
import com.dto.LoginDto;
import com.service.MailService;
import com.service.TokenService;

@RestController
public class SessionController {

	@Autowired
	UserDao userdao;

	@Autowired
	TokenService token;

	@Autowired
	BCryptPasswordEncoder bcrypt;
	@Autowired
	MailService mailerService;

//signup
	@PostMapping("/signup")

	public ResponseEntity signup(@RequestBody UserBean user) {

		UserBean dbuser = userdao.getUserByEmail(user.getEmail());
		if (dbuser != null) {
			return ResponseEntity.unprocessableEntity().body(user);
		} else {
			System.out.println(user.getEmail());
			String encodedPasword = bcrypt.encode(user.getPassword());
			user.setPassword(encodedPasword);
			LocalDate d = LocalDate.now();
			user.setCreatedAt(d.toString());
			user.setRole(UserBean.Role.USER.getRoleId());
			userdao.createUser(user);
			return ResponseEntity.ok(user);
		}
	}

//login
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

//forgetpassword	
	@PostMapping("/forgetpassword")
	public ResponseEntity forgetpassword(@RequestBody LoginDto loginDto) {
		UserBean user = userdao.getUserByEmail(loginDto.getEmail());

		if (user != null) {
			String otp = token.generateToken(6);
			user.setOtp(otp);
			userdao.updateOtp(user.getEmail(), otp);
			mailerService.sendMail(user);
		}
		return ResponseEntity.ok(loginDto);
	}

	// update password
	@PostMapping("/updatepassword")
	public ResponseEntity updatePassword(@RequestBody ForgetPasswordDto fdto) {
		UserBean user = userdao.getUserByEmail(fdto.getEmail());

		if (user != null) {
			// db check
			if (user.getOtp().equals(fdto.getOtp())) {
				userdao.updateOtp(user.getEmail(), "");
				String enc = bcrypt.encode(fdto.getPassword());
				userdao.updatePass(user.getEmail(), enc);
			}
			return ResponseEntity.ok(fdto);

		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fdto);
	}

}

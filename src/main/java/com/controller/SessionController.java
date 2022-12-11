package com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.UserBean;
//public api accesss by all without any token accesss
@RestController
public class SessionController {
	@PostMapping("/signup")
	public ResponseEntity signup(@RequestBody UserBean user)
	{
		return ResponseEntity.ok(user);
	}
}

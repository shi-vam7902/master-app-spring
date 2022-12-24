package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.UserBean;

@Repository
public class UserDao {

	// allocated memory without new keyword #singleton
	@Autowired
	JdbcTemplate stmt;

	public void createUser(UserBean userBean) {
		stmt.update("insert into users (firstname , email , password, createdAt,role) values (?,?,?,?,?)", userBean.getFirstname(),
				userBean.getEmail(), userBean.getPassword(),userBean.getCreatedAt(),userBean.getRole());
	}

	public UserBean getUserByEmail(String email) {
		try {
			
			return stmt.queryForObject("select * from users where email = ? ",
					new BeanPropertyRowMapper<UserBean>(UserBean.class), new Object[] { email });
		} catch (Exception e) {
			
			System.out.println("User Not found with this email==> "+email);
			
			return null;
		}

	}
	public void updateToken(String email , String authToken) {
		stmt.update("update users set authToken = ? where email = ?",authToken,email);
	}

	public void updateOtp(String email, String otp) {
		stmt.update("update users set otp = ? where email = ?",otp,email);
	}
	public void updatePass(String email, String enc) {
		stmt.update("update users set password = ? where email = ?",enc,email);
	}
	
}

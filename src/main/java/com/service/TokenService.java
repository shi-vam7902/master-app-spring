package com.service;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

	public String generateToken(int size) {
		if (size > 30) {
			size = 25;
		}
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(size);

		for (int i = 0; i < size; i++) {

			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();

	}
}

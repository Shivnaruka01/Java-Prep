package com.login.validation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
class User{
	public static final String USERNAME_PATTERN = "^[a-zA-Z0-9]{3,15}$";
	public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$";
	
	private String username;
	private String passwordHash;
	
	public User(String username, String rawPassword) {
		this.username = username;
		this.passwordHash = hashPassword(rawPassword);
	}	
	
	public String getUsername() {
		return username;
	}
	public String getPasswordHash() {
		return passwordHash;
	}

	public static String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes());
			StringBuilder hexString = new StringBuilder();
			for(byte b : hash) {
				hexString.append(String.format("%02x", b));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		
	}
}
public class LoginAttemptSystem {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		User admin = new User("admin", "Admin@123");
		int maxAttempts = 3;
		boolean isLoggedIn = false;
		
		for(int i = 1; i <= maxAttempts ; i++) {
			
		System.out.println("Enter Username: ");
		String inputUsername = sc.nextLine();
		System.out.println("Enter Password: ");
		String inputPassword = sc.nextLine();
		
		if(!inputUsername.matches(User.USERNAME_PATTERN) || !inputPassword.matches(User.PASSWORD_PATTERN)) {
	        System.out.println("❌ Invalid Format! Username (3-15 alphanumeric) and Password (8+ chars, 1 number, 1 special char) required.");
	        continue;
		}
		String hashedInput = User.hashPassword(inputPassword);
		if(admin.getUsername().equals(inputUsername) && admin.getPasswordHash().equals(hashedInput)) {
			System.out.println("Login successful");
			isLoggedIn = true;
			break;
		}else {
			System.out.println("Invalid credential");
			int left = maxAttempts - i;
			if(left > 0) System.out.println(left + " attempts remaining");
		}
		
		}
		if(!isLoggedIn) {
			System.out.println("Account Locked");
		}
		sc.close();
	}

}

package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.UserInfo;

public interface UserService {
	
	/**
	 * {@inheritDoc}
	 */
	public Optional<UserInfo> searchUserByEmail(String email);
}

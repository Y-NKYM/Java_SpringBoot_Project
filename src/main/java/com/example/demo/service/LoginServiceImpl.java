package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
	
	private final UserRepository repository;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<UserInfo> searchUserByEmail(String email){
		return repository.findByEmail(email);
	}
}

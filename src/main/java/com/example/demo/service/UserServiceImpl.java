package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
private final UserRepository repository;
	
	@Override
	public Optional<UserInfo> searchUserByEmail(String email){
		return repository.findByEmail(email);
	}
}

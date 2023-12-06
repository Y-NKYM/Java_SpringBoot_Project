package com.example.demo.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.RegisterForm;
import com.example.demo.repository.UserRepository;
import com.github.dozermapper.core.Mapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService{
	
	private final UserRepository repository;
	private final Mapper mapper;
	private final PasswordEncoder passwordEncoder;
	
	public Optional<UserInfo> registerUser(RegisterForm form){
		Optional<UserInfo> existedUser = repository.findByEmail(form.getEmail());
		
		//メールアドレスが既に存在している場合：
		if(existedUser.isPresent()) {
			return Optional.empty();
		}
		//メールアドレスが存在していない場合：
		UserInfo user = mapper.map(form, UserInfo.class);
		String encodedPassword = passwordEncoder.encode(form.getPassword());
		user.setPassword(encodedPassword);
		
		return Optional.of(repository.save(user));
	}
}

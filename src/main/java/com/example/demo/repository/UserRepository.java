package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, String>{
	Optional<UserInfo> findByEmail(String email);
}

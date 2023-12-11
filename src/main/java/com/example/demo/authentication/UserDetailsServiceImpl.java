package com.example.demo.authentication;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	/** ユーザー情報テーブルRepository */
	private final UserRepository repository;
	
	/** アカウントロック継続時間 */
	@Value("${security.locking-time}")
	private int lockingTime;
	
	/** アカウントロックとなるログイン失敗回数 */
	@Value("${security.locking-border-count}")
	private int lockingBorderCount;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		UserInfo user = repository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		
		LocalDateTime accountLockedTime = user.getAccountLockedTime();
		boolean isAccountLocked = accountLockedTime != null
				&& accountLockedTime.plusMinutes(lockingTime).isAfter(LocalDateTime.now());
		
		return User.withUsername(user.getEmail())
				.password(user.getPassword())
				.authorities(user.getAuthority())
				
				.disabled(user.isDisabled())
				.accountLocked(isAccountLocked)
				.build();
	}
	
	/**
	 * 認証失敗時にログイン失敗回数を加算し、失敗回数が上限に達した場合ロック日時を更新します。
	 * 
	 * @param event 認証失敗イベント(AuthenticationFailureBadCredentialsEvent)
	 */
	@EventListener  /* 引数に記述したイベントが発生したときにメソッドを実行 */
	public void handle(AuthenticationFailureBadCredentialsEvent event) {
		String email = event.getAuthentication().getName();
		repository.findByEmail(email).ifPresent(user -> {
			repository.save(user.incrementLoginFailureCount());
			
			boolean isReachFailureCount = user.getLoginFailureCount() == lockingBorderCount;
			if(isReachFailureCount) {
				repository.save(user.updateAccountLocked());
			}
		});
	}
	
	/**
	 * 認証成功時にログイン失敗回数とアカウント日時をリセット
	 * 
	 * @param event 認証成功イベント(AuthenticationSuccessEvent)
	 */
	@EventListener
	public void handle(AuthenticationSuccessEvent event) {
		String email = event.getAuthentication().getName();
		repository.findByEmail(email).ifPresent(user -> {
			repository.save(user.resetLoginFailureInfo());
		});
	}
	
	
}

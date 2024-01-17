package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;
import com.example.demo.entity.converter.UserAuthorityConverter;
import com.example.demo.entity.converter.UserStatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private String userId;
	private String name;
	private String email;
	private String password;
	
	/** ログイン失敗回数 */
	@Column(name="login_failure_count")
	private int loginFailureCount;
	
	/** アカウントロック日時 */
	@Column(name="account_locked_time")
	private LocalDateTime accountLockedTime;
	
	/** アカウントの利用可否（trueなら利用可能） */
	@Column(name="is_disabled")
	@Convert(converter = UserStatusConverter.class)
	private UserStatusKind status;
	
	/** ユーザー権限 */
	@Convert(converter = UserAuthorityConverter.class)
	private AuthorityKind authority;
	
	@OneToMany(mappedBy="user")
	private List<TodolistInfo> todolists;
	
	public UserInfo incrementLoginFailureCount() {
		return new UserInfo(userId, name, email, password, ++loginFailureCount, accountLockedTime, status, authority, todolists);
	} 
	
	public UserInfo updateAccountLocked() {
		return new UserInfo(userId, name, email, password, 0, LocalDateTime.now(), status, authority, todolists);
	}
	
	public UserInfo resetLoginFailureInfo() {
		return new UserInfo(userId, name, email, password, 0, null, status, authority, todolists);
	}
	
	
}

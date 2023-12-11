package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private boolean isDisabled;
	
	/** ユーザー権限 */
	@Column(name="authority")
	private String authority;
	
	public UserInfo incrementLoginFailureCount() {
		return new UserInfo(userId, name, email, password, ++loginFailureCount, accountLockedTime, isDisabled, authority);
	} 
	
	public UserInfo updateAccountLocked() {
		return new UserInfo(userId, name, email, password, 0, LocalDateTime.now(), isDisabled, authority);
	}
	
	public UserInfo resetLoginFailureInfo() {
		return new UserInfo(userId, name, email, password, 0, null, isDisabled, authority);
	}
}

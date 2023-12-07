package com.example.demo.form;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterForm {
	
	/** ユーザー名 */
	@NotEmpty
	@Size(max=20, message="{max}桁以下でご入力ください。")
	private String name;
	
	/** メールアドレス */
	@NotEmpty
	@Email
	private String email;
	
	/** パスワード */
	@NotEmpty
	@Size(min=8, message="{min}桁以上でご入力ください。")
	private String password;
}

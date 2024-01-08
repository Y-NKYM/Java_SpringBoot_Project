package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.ui.ModelMap;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

@Configuration
public class BeanDefine {
	
	/** PasswordEncoder パスワードで使用するハッシュ化のエンコーダー */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/** Mapper クラス間の変数の受け渡し代入 */
	@Bean
	Mapper mapper() {
		return DozerBeanMapperBuilder.buildDefault();
	}
	
	/** SecurityContextRepository リクエスト間のセキュリティ情報を永続化する */
	@Bean
	SecurityContextRepository securityContextRepository() {
	    return new HttpSessionSecurityContextRepository();
	}
	
	/** AuthenticationManager 渡されたAuthenticationオブジェクトの認証を試みる */
	@Bean
    AuthenticationManager authenticationManager(
        final AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
    }
	
	@Bean
	ModelMap modelMap() {
		return new ModelMap();
	}
	
}

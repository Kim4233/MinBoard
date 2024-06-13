package com.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {
		
	
	@Bean
	public PasswordEncoder encoder() {
		//非暗号化方式でパスワードを暗号化するオブジェクト
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChian(HttpSecurity http) throws Exception {
		http
			//Cross-Site-Request-Forgery 保護機能 
			.csrf().disable() 
			//iframeでアクセシビリティを無効にする設定を無効にする（iframeでアクセシビリティを有効にします。）
			.headers().frameOptions().disable()
			.and()
			//URL別に関するアクセス制御
			.authorizeRequests()
			.antMatchers("/","member/join","/member/login","/member/login-failed","/member/logout").permitAll()
			.antMatchers("/css/*","/js/*", "/favicon.ico","/error").permitAll()		
			//他のすべてのパスは認証されなければアクセスできません
			.anyRequest().authenticated()
			.and()
			//フォームログイン方式を使用します。
			.formLogin()
			//IDフィールドのデフォルト値はusernameで、他の名前として使用するときの名前を指定します。
			.usernameParameter("member_id")
			//開発者が作成したログインページを使用します。
			  //設定しないと、デフォルト値は '/login'なので、Springが使用するデフォルトのログインページが呼び出されます。
			.loginPage("/member/login")
            //ログイン認証処理を行うURL
			.loginProcessingUrl("/member/login")
			//ログインに成功したときに移動するURL
			.defaultSuccessUrl("/member/login-success")
			//ログインに失敗したときに移動するURL
			.failureUrl("/member/login-failed");
			
		return http.build();
	}
}

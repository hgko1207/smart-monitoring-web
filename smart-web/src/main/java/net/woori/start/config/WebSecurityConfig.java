package net.woori.start.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import net.woori.start.provider.SaveIdLoginSuccessHandler;
import net.woori.start.provider.UserAuthenticationProvider;

@Configuration
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserAuthenticationProvider authenticationProvider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests() // 요청을 어떻게 보안을 할 것인지 설정
			.antMatchers("/home").access("hasRole('ROLE_USER')")
			.antMatchers("/map").access("hasRole('ROLE_USER')")
			.antMatchers("/measurement").access("hasRole('ROLE_USER')")
		.and()
			// 로그인 페이지 및 성공 url, handler 그리고 로그인 시 사용되는 id, password 파라미터 정의
			.formLogin()
			.loginPage("/login")
			.failureUrl("/login?error")
			.usernameParameter("username")
	        .passwordParameter("password")
	        .successHandler(saveIdLoginSuccessHandler())
        .permitAll()
		.and()
			// 로그아웃 관련 설정
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login")
			// 로그아웃 성공 시 현재 보고 있는 페이지 리다이렉트
			.invalidateHttpSession(true)
		.and()
		.csrf().disable();
		
		// session 관리
		http.sessionManagement().sessionFixation().changeSessionId()
			.maximumSessions(5).expiredUrl("/home");
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) {
		// 로그인 프로세스가 진행될 provider
        auth.authenticationProvider(authenticationProvider);
    }
	
	@Bean
	public SaveIdLoginSuccessHandler saveIdLoginSuccessHandler() {
		SaveIdLoginSuccessHandler handler = new SaveIdLoginSuccessHandler();
		handler.setDefaultTargetUrl("/home");
		handler.setAlwaysUseDefaultTargetUrl(true);
		
		return handler;
	}
}

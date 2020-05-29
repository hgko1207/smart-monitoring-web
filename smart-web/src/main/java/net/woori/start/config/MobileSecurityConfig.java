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
@Order(1)
public class MobileSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserAuthenticationProvider authenticationProvider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.antMatcher("/m/**")
			.authorizeRequests()
				.antMatchers("/m/**").access("hasRole('ROLE_USER')")
				.anyRequest().authenticated()
		.and()
			// 로그인 페이지 및 성공 url, handler 그리고 로그인 시 사용되는 id, password 파라미터 정의
			.formLogin()
			.loginPage("/m/login")
			.failureUrl("/m/login?error")
			.usernameParameter("username")
	        .passwordParameter("password")
	        .successHandler(saveIdLoginSuccessHandler2())
        .permitAll()
		.and()
			// 로그아웃 관련 설정
			.logout()
			.logoutUrl("/m/logout")
			.logoutSuccessUrl("/m/")
			// 로그아웃 성공 시 현재 보고 있는 페이지 리다이렉트
			.invalidateHttpSession(true)
		.and()
		.csrf().disable();
		
		// session 관리
		http.sessionManagement().sessionFixation().changeSessionId()
			.maximumSessions(5).expiredUrl("/m/home");
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) {
		// 로그인 프로세스가 진행될 provider
        auth.authenticationProvider(authenticationProvider);
    }
	
	@Bean
	public SaveIdLoginSuccessHandler saveIdLoginSuccessHandler2() {
		SaveIdLoginSuccessHandler handler = new SaveIdLoginSuccessHandler();
		handler.setDefaultTargetUrl("/m/home");
		handler.setAlwaysUseDefaultTargetUrl(true);
		
		return handler;
	}
}

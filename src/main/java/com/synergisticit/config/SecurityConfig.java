package com.synergisticit.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {
	@Autowired DataSource dataSource;
	
//	@Autowired BCryptPasswordEncoder bCrypt;
	@Autowired UserDetailsService userDetailsService ;
    @Autowired AccessDeniedHandler accessDeniedHandler;
    @Autowired AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Bean
	DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		
		return authProvider;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable() //csrf=cross site request forgery
		.authorizeHttpRequests()
		.requestMatchers("/").permitAll()
		.requestMatchers("/login").permitAll()
		.requestMatchers("/user/form", "/user/","/user/saveUser").permitAll()
		.requestMatchers("/transaction/form", "/transaction/","/transaction/saveTransaction").permitAll()
		.requestMatchers("/WEB-INF/jsp/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login") // login is a URL
		.successHandler(authenticationSuccessHandler)
		.and()
		.exceptionHandling()
		.accessDeniedHandler(accessDeniedHandler);
		
		return http.build();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/images/**", "/css/**");
	}
}
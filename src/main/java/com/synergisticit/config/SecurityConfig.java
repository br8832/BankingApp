package com.synergisticit.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig{
	@Autowired DataSource dataSource;
	@Autowired UserDetailsService userDetailsService ;
    @Autowired AccessDeniedHandler accessDeniedHandler;
    @Autowired AuthenticationSuccessHandler authenticationSuccessHandler;

    
	@Bean
	DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		
		return authProvider;
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
	        http.csrf(csrf->csrf.disable())
	            .authorizeRequests(requests->requests
	            		.requestMatchers("/branch/**","/role/**").hasAnyAuthority("Admin")
	            		.requestMatchers("/WEB-INF/jsp/**","/", "/login","/transaction/**","/customer/**","/account/**").permitAll()
		                .requestMatchers("/static/**","/resources/**","/mp3/**","/images/**", "/css/**").permitAll()
		                .anyRequest().authenticated())
	            .formLogin(login->login.successHandler(authenticationSuccessHandler))
	            .exceptionHandling(error->error.accessDeniedHandler(accessDeniedHandler));
	        return http.build();
	    }
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/static/**","/resources/**","/mp3/**","/images/**", "/css/**");
	}
	
}
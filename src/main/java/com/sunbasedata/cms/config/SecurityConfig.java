package com.sunbasedata.cms.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.sunbasedata.cms.security.JwtAuthenticationEntryPoint;
import com.sunbasedata.cms.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {


	private Logger logger = org.slf4j.LoggerFactory.getLogger(SecurityConfig.class);
	
		
		
		@Autowired
		private JwtAuthenticationEntryPoint entryPoint;
		@Autowired
		private JwtAuthenticationFilter filter;

		 
		
		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
			logger.info(" fillter chain excuted ");
		
		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
		.authorizeHttpRequests(auth -> auth.requestMatchers("/api/customer/**").authenticated().requestMatchers("/auth/login").permitAll().anyRequest().authenticated())
		.exceptionHandling(ex->ex.authenticationEntryPoint(entryPoint))
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
		
		
		@Bean
		public FilterRegistrationBean<?> corsFilter() {
			
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			
			CorsConfiguration corsConfig = new CorsConfiguration();
			corsConfig.setAllowCredentials(true);
			corsConfig.addAllowedOriginPattern("*");
			corsConfig.addAllowedHeader("Authorization");
			corsConfig.addAllowedHeader("Content-Type");
			corsConfig.addAllowedHeader("Accept");
			corsConfig.addAllowedMethod("GET");
			corsConfig.addAllowedMethod("POST");
			corsConfig.addAllowedMethod("PUT");
			corsConfig.addAllowedMethod("DELETE");
			corsConfig.addAllowedMethod("OPTIONS");
			corsConfig.setMaxAge(3600L);
			
			source.registerCorsConfiguration("/**", corsConfig);
			
			
			return new FilterRegistrationBean<>(new CorsFilter(source));
		}
}

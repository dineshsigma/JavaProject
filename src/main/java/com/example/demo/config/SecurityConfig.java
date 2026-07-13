package com.example.demo.config;

import com.example.demo.filter.HeaderAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import com.example.demo.handler.CustomAccessDeniedHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	private final HeaderAuthFilter headerAuthFilter;
	private final CustomAccessDeniedHandler accessDeniedHandler;

	public SecurityConfig(HeaderAuthFilter headerAuthFilter, CustomAccessDeniedHandler accessDeniedHandler) {
		this.headerAuthFilter = headerAuthFilter;
		this.accessDeniedHandler = accessDeniedHandler;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.cors(cors -> {
		}).csrf(csrf -> csrf.disable())

				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/auth/**","/api/admins/stream", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**")
						.permitAll().requestMatchers("/auth/**").permitAll()
						.requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll().anyRequest()
						.authenticated())
				.exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler))

				.addFilterBefore(headerAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// ✅ ADD THIS (CRITICAL)
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
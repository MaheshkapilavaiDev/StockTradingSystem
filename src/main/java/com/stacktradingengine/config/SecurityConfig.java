package com.stacktradingengine.config;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.stacktradingengine.entity.User;
import com.stacktradingengine.enums.Role;
import com.stacktradingengine.repository.UserRepository;
import com.stacktradingengine.security.JwtFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtFilter jwtFilter;

	public SecurityConfig(JwtFilter jwtFilter) {

		this.jwtFilter = jwtFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())

				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.authorizeHttpRequests(auth -> auth

						.requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

						.anyRequest().authenticated())

				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner initAdmin(UserRepository repository, PasswordEncoder encoder) {

		return args -> {

			Optional<User> admin = repository.findByEmail("admin@gmail.com");

			if (admin.isEmpty()) {

				User user = new User();

				user.setName("SuperAdmin");
				user.setEmail("admin@gmail.com");
				user.setPassword(encoder.encode("admin123"));

				user.setRole(Role.ADMIN);

				repository.save(user);

				System.out.println("Admin Created");
			}
		};
	}
}

package com.id.akn.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class AppConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/api/payment/ipn").permitAll()
						.requestMatchers("/api/payment/return").permitAll()
						.requestMatchers("/api/admin/**").hasRole("ADMIN")
						// .requestMatchers("/api/**").authenticated()
						.anyRequest().permitAll()
				)
				.addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
				.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> cors.configurationSource(request -> {
					CorsConfiguration cfg = new CorsConfiguration();
					cfg.setAllowedOrigins(Arrays.asList(
							"http://localhost:3000",
							"http://localhost:4200",
							"http://14.225.29.152:3000",
							"http://14.225.29.152"
					));
//					cfg.setAllowedOrigins(Arrays.asList("*"));
					cfg.setAllowedMethods(Collections.singletonList("*"));
					cfg.setAllowCredentials(true);
					cfg.setAllowedHeaders(Collections.singletonList("*"));
					cfg.setExposedHeaders(List.of("Authorization"));
					cfg.setMaxAge(3600L);
					return cfg;
				}))
				.httpBasic(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable);
		return http.build();
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
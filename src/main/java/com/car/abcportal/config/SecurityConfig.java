package com.car.abcportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http	
        	.csrf(
        			csrf -> csrf.disable()
        	)
        	.authorizeRequests( auth -> {
                auth.requestMatchers("/").permitAll();
                auth.requestMatchers("/login").permitAll();
                auth.requestMatchers("/register_user").permitAll();
                auth.requestMatchers("/car").hasAnyRole("Administrator", "Users");
                auth.requestMatchers(HttpMethod.GET, "/profile").hasAnyRole("Users","Administrator");
                auth.requestMatchers(HttpMethod.GET, "/car_detail").hasAnyRole("Users","Administrator");
                auth.requestMatchers(HttpMethod.GET, "/users").hasRole("Administrator");
                auth.requestMatchers(HttpMethod.GET, "/view").hasRole("Administrator");
                auth.requestMatchers(HttpMethod.POST, "/edit").hasRole("Administrator");
                auth.requestMatchers(HttpMethod.GET, "/delete").hasRole("Administrator");
                auth.requestMatchers(HttpMethod.GET, "/all_cars").hasRole("Administrator");
                auth.requestMatchers(HttpMethod.POST, "/edit_car").hasRole("Administrator");
                auth.requestMatchers(HttpMethod.GET, "/delete_car").hasRole("Administrator");
                auth.requestMatchers(HttpMethod.GET, "/deleteCar").hasRole("Users");
        	})
            .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .failureUrl("/login_error")
                                .defaultSuccessUrl("/car")
                                .permitAll()
             )
            
             .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
                                .permitAll()

              );
        return http.build();
    }

}
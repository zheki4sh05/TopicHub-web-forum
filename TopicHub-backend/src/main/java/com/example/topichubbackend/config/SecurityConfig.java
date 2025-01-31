package com.example.topichubbackend.config;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.impls.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.jdbc.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.security.web.authentication.www.*;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfiguration{

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        var auth = new BasicAuthenticationEntryPoint();
        auth.setRealmName("forum");
        return http
                .csrf()
                .disable()
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests->
                    authorizeRequests
                          .requestMatchers("/api/v1/auth/**").permitAll()
                            .requestMatchers("/admin/**").hasAuthority(RoleDto.ADMIN.type())
                            .requestMatchers("/api/v1/logout").permitAll()
                            .requestMatchers("/api/v1/article").permitAll()
                            .requestMatchers("/api/v1/answers").permitAll()
                            .requestMatchers("/api/v1/image").permitAll()
                            .requestMatchers("/api/v1/hubs").permitAll()
                            .anyRequest().authenticated()
        )
                .logout(
                        logout->logout.logoutUrl("/logout")
                                .addLogoutHandler(new SecurityContextLogoutHandler())
                )
                .httpBasic(httpSecurityHttpBasicConfigurer ->httpSecurityHttpBasicConfigurer.authenticationEntryPoint(auth)).build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}

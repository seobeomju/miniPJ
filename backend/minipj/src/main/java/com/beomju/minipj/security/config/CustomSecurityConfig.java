package com.beomju.minipj.security.config;

import com.beomju.minipj.security.filter.JWTCheckFilter;
import com.beomju.minipj.security.handler.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;
import java.util.List;

@Log4j2
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class CustomSecurityConfig {

    private final JWTCheckFilter jwtCheckFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        log.info("-------------configure-------");

        //모든 요청 인증 없이 허용 (개발 초기)
//        http.authorizeHttpRequests(auth -> auth
//                .anyRequest().permitAll()
//        );

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/todos/**").hasRole("USER") // 예: Todo API는 USER 권한 필요
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN") // ADMIN 전용 API
                .anyRequest().permitAll()
        );

        http.cors(cors -> {
            cors.configurationSource(corsConfigurationSource());
        });

        //로그인 화면 필요 없음
        http.formLogin(config -> {
            config.disable();

        });

        http.exceptionHandling(config -> {
            config.accessDeniedHandler(new CustomAccessDeniedHandler());
        });

        //CSRF Token 비활성화
        http.csrf(config -> config.disable());

        //세션 생성 필요 없음
        http.sessionManagement(config -> {
            config.sessionCreationPolicy(SessionCreationPolicy.NEVER);
        });


        http.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class );



        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(List.of("*")); // 어디서든 허락
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }



}
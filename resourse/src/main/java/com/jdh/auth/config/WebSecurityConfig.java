package com.jdh.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableOAuth2Client
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .httpBasic()
                .realmName("concrete")
        .and()
            .oauth2Login()
                .loginProcessingUrl("/oauth/token")
                .permitAll()
        .and()
            .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .permitAll()
        .and()
            .requestMatchers()
                .antMatchers("/login", "/logout", "/oauth/authorize", "/oauth/confirm_access")
        .and()
            .authorizeRequests()
                .anyRequest()
                .authenticated()
        .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/home")
                .permitAll()
        .and()
            .cors()
        .and()
            .csrf()
                .disable();
        // @formatter:on
    }

//    @Bean
//    public CustomCorsFilter corsFilter() {
//        return new CustomCorsFilter();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));
//        configuration.setMaxAge(180L);
//        configuration.setAllowCredentials(false);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/dev/**", configuration);
//        source.registerCorsConfiguration("/dev/oauth/**", configuration);
//        source.registerCorsConfiguration("/oauth/**", configuration);
//        source.registerCorsConfiguration("/oauth/token", configuration);
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    @Override
    public void init(WebSecurity web) {
        web.ignoring().antMatchers("/oapi/**", "/auth/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.parentAuthenticationManager(authenticationManager);
    }

}

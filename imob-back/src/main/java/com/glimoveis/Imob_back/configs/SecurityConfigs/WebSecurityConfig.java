package com.glimoveis.Imob_back.configs.SecurityConfigs;

import com.glimoveis.Imob_back.configs.CorsConfigs.CorsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private SecurityFilter securityFilter;

    private CorsConfig corsConfig;

    private CustomAcessDeniedHandler customAcessDeniedHandler;

    public WebSecurityConfig(SecurityFilter securityFilter, CorsConfig corsConfig, CustomAcessDeniedHandler customAcessDeniedHandler){
        this.corsConfig = corsConfig;
        this.securityFilter = securityFilter;
        this.customAcessDeniedHandler = customAcessDeniedHandler;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        return http.csrf(csrf -> csrf.disable()).cors().configurationSource(corsConfig.corsConfigurationSource()).and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((request) -> request
                        .requestMatchers(HttpMethod.POST, "/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/imoveis").permitAll()
                        .requestMatchers(HttpMethod.GET, "/login/oauth").permitAll()
                        .requestMatchers(HttpMethod.GET, "/imoveis/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/imoveis/type/*").permitAll()
                        .anyRequest()
                        .authenticated())
                //.oauth2Login(oauth ->
                        //oauth.successHandler(oauth2AuthenticationSuccessHandler())
                //)
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAcessDeniedHandler).and()
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler() {
        return new CustomOAuth2AuthenticationSuccessHandler();
    }

}

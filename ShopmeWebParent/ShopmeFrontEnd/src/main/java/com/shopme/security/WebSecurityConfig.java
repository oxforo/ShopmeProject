package com.shopme.security;

import com.shopme.security.oauth.CustomerOAuth2UserService;
import com.shopme.security.oauth.OAuth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired private CustomerOAuth2UserService oAuth2UserService;
    @Autowired private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    @Autowired private DataBaseLoginSuccessHandler dataBaseLoginSuccessHandler;

    @Bean
    public UserDetailsService customerDetailsService() {
        return new CustomerUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customerDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests((authz) -> {
                            try {
                                authz
                                        .antMatchers("/account_details", "/update_account_details",
                                                "/cart", "/address_book/*").authenticated()
                                        .anyRequest().permitAll()
                                        .and()
                                        .formLogin()
                                            .loginPage("/login")
                                            .usernameParameter("email")
                                            .successHandler(dataBaseLoginSuccessHandler)
                                            .permitAll()
                                        .and()
                                        .oauth2Login()
                                            .loginPage("/login")
                                            .userInfoEndpoint()
                                            .userService(oAuth2UserService)
                                            .and()
                                            .successHandler(oAuth2LoginSuccessHandler)
                                        .and()
                                            .logout().permitAll()
                                        .and()
                                            .rememberMe()
                                            .key("ABCDEFGHIJKLMNOPQR_1234567890")
                                            .tokenValiditySeconds(14 * 24 * 60 * 60)
                                        .and()
                                            .sessionManagement()
                                            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }

}
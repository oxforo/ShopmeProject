package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new ShopmeUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests((authz) -> {
                            try {
                                authz
                                        .antMatchers("/users/**").hasAuthority("Admin")
                                        .antMatchers("/brands/**").hasAnyAuthority("Admin", "Editor")
                                        .antMatchers("/categories/**").hasAnyAuthority("Admin", "Editor")

                                        // create, delete - admin & editor
                                        .antMatchers("/products/new", "/products/delete/**")
                                        .hasAnyAuthority("Admin", "Editor")

                                        // update - admin & editor & salesperson
                                        .antMatchers("/products/{\\d+}", "/products/check_unique")
                                            .hasAnyAuthority("Admin", "Editor", "Salesperson")
                                        .antMatchers(HttpMethod.POST, "/products")
                                            .hasAnyAuthority("Admin", "Editor", "Salesperson")

                                        // show list
                                        .antMatchers(HttpMethod.GET,"/products")
                                            .hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
                                        .antMatchers("/products/", "/products/detail/**", "/products/page/**")
                                            .hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")

                                        .antMatchers("/products/**").hasAnyAuthority("Admin", "Editor")

                                        .anyRequest().authenticated()
                                        .and()
                                        .formLogin()
                                        .loginPage("/login")
                                        .usernameParameter("email")
                                        .permitAll()
                                        .and().logout().permitAll()
                                        .and()
                                        .rememberMe()
                                        .key("ABCDEFGHIJKLMNOPQR_1234567890")
                                        .tokenValiditySeconds(7 * 24 * 60 * 60);
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
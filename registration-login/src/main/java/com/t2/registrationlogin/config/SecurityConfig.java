/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.registrationlogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationFailureHandler authFailureHandler;

//    @Autowired
//    private SimpleUrlLogoutSuccessHandler myLogoutSuccessHandler;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //1. dùng authenticationProvider
        //auth.authenticationProvider(authProvider);

        //2. dùng userDetailService
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        //3. account trong memory
//        auth.inMemoryAuthentication().withUser("user")
//                .password("1").roles("USER")
//                .and()
//                .withUser("admin")
//                .password("1").roles("USER", "ADMIN");
    }
    private static final Logger logger = Logger.getLogger(SecurityConfig.class.getName());

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // bỏ qua csrf cho form đăng kí
        http.csrf().ignoringAntMatchers("/u/registration");

        // Các trang không yêu cầu login
        http.authorizeRequests().antMatchers("/u/login", "/u/logout").permitAll();

        // yêu cầu quyền 
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/u/changePassword*").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/u/changePassword*")
                .hasAuthority("CHANGE_OWN_PASSWORD");

        // Trang /tnfo yêu cầu phải login với vai trò ROLE_USER hoặc ROLE_ADMIN.
        // Nếu chưa login, nó sẽ redirect tới trang /login.
        http.authorizeRequests()
                .antMatchers("/u/info").hasAuthority("READ_INFO");

        // Trang chỉ dành cho ADMIN
        http.authorizeRequests()
                .antMatchers("/u/admin").access("hasAuthority('WRITE_INFO') and hasAuthority('READ_INFO')");

        // Khi người dùng đã login, với vai trò XX.
        // Nhưng truy cập vào trang yêu cầu vai trò YY,
        // Ngoại lệ AccessDeniedException sẽ ném ra.
        http.authorizeRequests()
                .and().exceptionHandling().accessDeniedPage("/u/403");

        // Cấu hình cho Login Form.
        http.authorizeRequests()
                .and().formLogin()//
                // Submit URL của trang login
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/u/login")
                //
                .defaultSuccessUrl("/u/info")
                //
                .failureHandler(authFailureHandler)
                //
                .usernameParameter("username")//
                .passwordParameter("password")
                // Cấu hình cho Logout Page.
                //...
                // cấu hình remember me
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me");
    }

}

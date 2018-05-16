/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.registrationlogin.config;

import com.t2.registrationlogin.entity.User;
import com.t2.registrationlogin.service.UserService;
import com.t2.registrationlogin.utils.FineGrained;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private FineGrained fineGrained;
    
    private static final Logger logger
            = Logger.getLogger(MyUserDetailsService.class.getName());

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        logger.info("Via UserDetailService");
        User user = service.getUserByEmail(email);
        if(user == null)
            throw new UsernameNotFoundException("Tài khoản không tồn tại");
        logger.info(user.toString());
        
        fineGrained.getAuthorities(user.getRoles()).forEach(System.out::println);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
                true, fineGrained.getAuthorities(user.getRoles()));
    }

    
}

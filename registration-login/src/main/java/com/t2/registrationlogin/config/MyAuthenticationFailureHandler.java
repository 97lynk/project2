/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.registrationlogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 97lynk
 */
@Service
public class MyAuthenticationFailureHandler
        extends SimpleUrlAuthenticationFailureHandler {

    private static final Logger logger
            = Logger.getLogger(MyAuthenticationFailureHandler.class.getName());

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        setDefaultFailureUrl("/u/login?error=true");

        super.onAuthenticationFailure(request, response, exception);
        
        Locale locale = localeResolver.resolveLocale(request);

        String errorMessage = exception.getMessage();

        logger.log(Level.WARNING, "Failure auth: {0}", errorMessage);

        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }
}

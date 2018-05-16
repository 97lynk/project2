/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.registrationlogin.exception;

import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ErrorController {

    private static final Logger logger = Logger.getLogger(ErrorController.class.getName());

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String exception(HttpServletRequest request, Exception exception, Model model) {
        logger.log(Level.WARNING, "Exception handler: {0}", exception.getMessage());
        String errorMessage = (exception != null) ? exception.getMessage() : "Unknown error";
        model.addAttribute("message", errorMessage);
        return "signup/badConfirmPage";
    }

}

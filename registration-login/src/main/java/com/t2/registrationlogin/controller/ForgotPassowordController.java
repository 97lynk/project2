/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.registrationlogin.controller;


import com.t2.registrationlogin.dto.*;
import com.t2.registrationlogin.entity.*;
import com.t2.registrationlogin.event.SendResetPassMailEvent;
import com.t2.registrationlogin.service.IUserService;
import com.t2.registrationlogin.utils.EmailStatus;
import com.t2.registrationlogin.utils.TokenStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 97lynk
 */
@Controller
public class ForgotPassowordController {

    private static final Logger logger
            = Logger.getLogger(ForgotPassowordController.class.getName());
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private IUserService userService;

    @Autowired
    private MessageSource messages;

    @RequestMapping(value = "/u/resetPassword",
            method = RequestMethod.GET)
    public String forgotPasswordPage() {
        return "signin/forgotPasswordPage";
    }

    @RequestMapping(value = "/u/resetPassword",
            method = RequestMethod.POST)
    public String resetPassword(
            WebRequest request, Model model,
            @RequestParam("email") String email) {
        logger.info(email);
        Optional<User> user = Optional.ofNullable(email).map(userService::getUserByEmail);
        Optional<PasswordResetToken> token = user.map(userService::getPasswordResetTokenByUser);
        String message = "message.checkSpam";
        String textColor = "alert alert-danger";
        boolean sendMail = false;

        EmailStatus status = userService.getStatusForEmail(user.orElse(null), token.orElse(null));
        logger.info("Status :" + status);

        switch (status) {
            case NOT_EXIST:
                message = "message.notEmail";
                break;
            case SPAM:
                message = "message.checkSpam";
                break;
            case SEND:
                message = "message.checkMail";
                textColor = "alert alert-success";
                sendMail = true;
                break;
            default:
                break;
        }

        // gửi mail
        if (sendMail) {
            try {
                // gửi bằng event reset pass
                eventPublisher.publishEvent(new SendResetPassMailEvent(
                        user.get(), request.getLocale(), request.getContextPath()));
            } catch (Exception ex) {
                logger.log(Level.WARNING, "has exception {0}", ex.getMessage());
            }
        }

        logger.log(Level.INFO, "{0} - {1}", new Object[]{email, message});
        model.addAttribute("result", messages.getMessage(message, null, request.getLocale()));
        model.addAttribute("textColor", textColor);
        return "signin/forgotPasswordPage";
    }

    @RequestMapping(value = "/u/changePassword",
            method = RequestMethod.GET)
    public String changePasswordPage(
            Model model,
            @RequestParam(value = "id", defaultValue = "") long id,
            @RequestParam(value = "token", defaultValue = "") String token) {
        TokenStatus status = userService.validatePasswordResetToken(id, token);
        logger.info(status.toString());
        if(status != TokenStatus.VALID){
            model.addAttribute("message", "id or token is " + status.toString());
            return "signup/badConfirmPage";
        }
        model.addAttribute("password", new PasswordDTO());
        logger.log(Level.INFO, "id: {0}", id);
        return "signin/changePasswordPage";
    }

    @RequestMapping(value = "/u/changePassword",
            method = RequestMethod.POST)
    public String changePassword(
            @ModelAttribute("password") @Valid PasswordDTO password,
            BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println);
            return "signin/changePasswordPage";
        }
        logger.info(password.toString());
        return "signin/loginPage";
    }

}

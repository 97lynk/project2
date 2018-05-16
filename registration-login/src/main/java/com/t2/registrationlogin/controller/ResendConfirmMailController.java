/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.registrationlogin.controller;


import com.t2.registrationlogin.entity.*;
import com.t2.registrationlogin.event.SendConfirmMailEvent;
import com.t2.registrationlogin.service.IUserService;
import com.t2.registrationlogin.utils.EmailStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 97lynk
 */
@Controller
public class ResendConfirmMailController {

    private static final Logger logger
            = Logger.getLogger(ResendConfirmMailController.class.getName());
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private MessageSource messages;

    @Autowired
    private IUserService userService;

    // gửi resend mail form
    @RequestMapping(value = "/u/resendRegistration", method = RequestMethod.GET)
    public String resendConfirmPage(Model model,
            @RequestParam(name = "email", defaultValue = "") String email) {
        model.addAttribute("email", email);
        model.addAttribute("result", null);
        return "signup/resendConfirmPage";
    }

    // submit resend
    @RequestMapping(value = "/u/resendRegistration", method = RequestMethod.POST)
    public String resendRegistrationToken(
            WebRequest request, Model model,
            @RequestParam("email") String email) {

        // lấy user bằng email
        Optional<User> user = Optional.ofNullable(email).map(userService::getUserByEmail);
        Optional<VerificationToken> token = user.map(userService::getVerificationToken);

        String message = "";
        String textColor = "alert alert-danger";
        boolean sendMail = false;

        EmailStatus status = userService.getStatusForEmail(user.orElse(null), token.orElse(null));
        logger.info("Status" + status);
        switch (status) {
            case NOT_EXIST:
                message = "message.notEmail";
                break;
            case ENABLE:
                message = "message.activitedEmail";
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
                eventPublisher.publishEvent(new SendConfirmMailEvent(
                        user.get(), request.getLocale(), request.getContextPath()));
            } catch (Exception ex) {
                logger.log(Level.WARNING, "has exception {0}", ex.getMessage());
            }
        }

        logger.log(Level.INFO, "{0} - {1}", new Object[]{email, message});
        model.addAttribute("result", messages.getMessage(message, null, request.getLocale()));
        model.addAttribute("textColor", textColor);
        return "signup/resendConfirmPage";

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.registrationlogin.controller;


import com.t2.registrationlogin.dto.*;
import com.t2.registrationlogin.entity.*;
import com.t2.registrationlogin.event.SendConfirmMailEvent;
import com.t2.registrationlogin.service.IUserService;
import com.t2.sendmail.exception.EmailExistsException;
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

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 97lynk
 */
@Controller
public class RegistrationController {
    
    private static final Logger logger
            = Logger.getLogger(RegistrationController.class.getName());
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @Autowired
    private MessageSource messages;
    
    @Autowired
    private IUserService userService;

    // send registration page
    @RequestMapping(value = "/u/registration", method = RequestMethod.GET)
    public String showRegistrationForm(@ModelAttribute("user") UserDTO userDTO, Locale locale) {
        logger.log(Level.INFO, "{0} {1}", new Object[]{locale.getCountry(), locale.toLanguageTag()});
        userDTO = new UserDTO();
        return "signup/registrationPage";
    }

    // submit registration form
    @RequestMapping(value = "/u/registration", method = RequestMethod.POST)
    public String registerUserAccount(
            Model model,
            @ModelAttribute("user") @Valid UserDTO userDTO,
            BindingResult result, WebRequest request)
            throws ConstraintViolationException {
        logger.info("Submit register");
        // kiểm tra lỗi
        if (result.hasErrors()) {
            logger.warning("has error");
            model.addAttribute("user", userDTO);
            return "signup/registrationPage";
        }

        // lấy tạo user mới theo userdto đã valid
        Optional<User> register = Optional.ofNullable(createUserAccount(userDTO, result));

        // send mail confirm
        try {
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new SendConfirmMailEvent(
                    register.orElse(null), request.getLocale(), appUrl));
        } catch (Exception ex) {
            logger.log(Level.WARNING, "has exception {0}", ex.getLocalizedMessage());
            model.addAttribute("user", userDTO);
            return "signup/registrationPage";
        }

        // chuyển trang success
        model.addAttribute("message", messages.getMessage("message.checkMail", null, request.getLocale()));
        model.addAttribute("user", register.orElse(new User()));
        return "signup/completeRegistrationPage";
    }

    // confirm link được gửi qua mail kèm token
    @RequestMapping(value = "/u/registrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(Locale locale, Model model,
            @RequestParam(name = "token", defaultValue = "") String token) {

        // tìm token trong db
        Optional<VerificationToken> verificationToken
                = Optional.of(token).map(userService::getVerificationToken);

        // token không tồn tại
        if (!verificationToken.isPresent()) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "signup/badConfirmPage";
        }

        // kiểm tra liệu tài khoản này đã xác nhận chưa
        Optional<User> user = verificationToken.map(VerificationToken::getUser);
        if (user.get().isEnabled()) {
            String message = messages.getMessage("message.activitedEmail", null, locale);
            model.addAttribute("message", message);
            return "signup/badConfirmPage";
        }
        
        Calendar cal = Calendar.getInstance();
        // kiểm tra thời hạn token
        if (verificationToken.map(VerificationToken::getExpiryDate).get()
                .before(cal.getTime())) {
            String message = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", message);
            model.addAttribute("mailAddress", user.map(User::getEmail).get());
            return "signup/badConfirmPage";
        }
        
        User u = user.orElse(new User());
        u.setEnabled(true);
        userService.saveRegisteredUser(u);
        model.addAttribute("user", u);
        return "signin/loginPage";
    }
    
    private User createUserAccount(UserDTO userDTO, BindingResult result) {
        User registered = null;
        try {
            registered = userService.registerNewUserAccount(userDTO);
            logger.info("Account i created");
        } catch (EmailExistsException emailExistsException) {
            result.rejectValue("email", "message.alreadyEmail");
            logger.warning(emailExistsException.getMessage());
            return null;
        } catch (ConstraintViolationException constraintViolationException) {
            result.rejectValue("matchingPassword", "message.dontMatchPass");
            logger.warning(constraintViolationException.getMessage());
            return null;
        }
        return registered;
    }
    
}

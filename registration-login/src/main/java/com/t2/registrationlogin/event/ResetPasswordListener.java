/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.registrationlogin.event;


import com.t2.registrationlogin.entity.*;
import com.t2.registrationlogin.service.IUserService;
import com.t2.sendmail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Logger;

@Component
public class ResetPasswordListener implements
        ApplicationListener<SendResetPassMailEvent> {

    @Autowired
    private IUserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Environment env;

    private static final Logger logger
            = Logger.getLogger(RegistrationListener.class.getName());

    // API
    @Override
    public void onApplicationEvent(SendResetPassMailEvent event) {
        Optional<User> user = Optional.of(event).map(SendResetPassMailEvent::getUser);
        Optional<PasswordResetToken> token = user.map(service::getPasswordResetTokenByUser);

        if (token.isPresent()) {
            token = token.map(PasswordResetToken::getToken)
                    .map(service::generateNewPasswordResetToken);

        } else {
            token = user.map(service::createPasswordResetTokenForUser);
        }
        this.sendConfirmChangePassword(token.get());
    }

    public void sendConfirmChangePassword(PasswordResetToken resetToken) {
        logger.info("Generate token");
        String token = resetToken.getToken();
        User user = resetToken.getUser();

        try {
            final String recipientAddress = user.getEmail();
            final String subject = "Reset Password";
            final String confirmationUrl = String.format("http://localhost:8080/u/changePassword?id=%s&token=%s",
                    user.getId(), token);
            final String content
                    = String.format("Hi! %s %s%n"
                            + "You want to reset password%n"
                            + "Click or paste it into your browser:%n"
                            + "%s%nThanks!",
                            user.getFirstName(), user.getLastName(),
                            confirmationUrl);

            emailService.sendSimpleMessage(recipientAddress, subject, content);

        } catch (MailException ex) {
            logger.warning(ex.getMessage());
        }

    }

}

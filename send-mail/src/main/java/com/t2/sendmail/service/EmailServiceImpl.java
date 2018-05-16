/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.sendmail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.logging.Logger;

/**
 *
 * @author 97lynk
 */
@Component
public class EmailServiceImpl implements EmailService {

    private static final Logger logger
            = Logger.getLogger(EmailServiceImpl.class.getName());

    @Autowired
    public JavaMailSender emailSender;

    @Value("${support.email}")
    String sender;

    @Override
    public void sendSimpleMessage(String to, String subject, String content)
            throws MailException {
        logger.info("Prepare email to " + to);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        emailSender.send(message);
        logger.info("Sent email: " + subject);
    }

    @Override
    public void sendSimpleMessageUsingTemplate(String to,
            String subject,
            SimpleMailMessage template,
            String... templateArgs) throws MailException {

        String text = String.format(template.getText(), templateArgs);
        sendSimpleMessage(to, subject, text);
    }

    @Override
    public void sendMessageWithAttachment(String to,
            String subject,
            String content,
            String pathToAttachment) throws MailException, MessagingException {
        logger.info("Prepare email to " + to);
        MimeMessage message = emailSender.createMimeMessage();
        // pass 'true' to the constructor to create a multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content);

        FileSystemResource file = new FileSystemResource(pathToAttachment);
        helper.addAttachment(file.getFilename(), file);

        emailSender.send(message);
        logger.info("Sent email: " + subject);
    }

}

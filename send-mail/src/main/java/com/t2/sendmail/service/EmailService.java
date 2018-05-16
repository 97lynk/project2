/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.sendmail.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;

/**
 *
 * @author 97lynk
 */
public interface EmailService {

     /**
     * Gửi email đơn giản
     * @param to người nhận
     * @param subject tiêu đề email
     * @param content nội dung email
     */
    void sendSimpleMessage(String to, String subject, String content)
            throws MailException;

    /**
 * Gửi email đơn giản
 * @param to người nhận
 * @param subject tiêu đề email
 * @param template SimpleMaillMessage
 * @param templateArgs Arguments
 */
    void sendSimpleMessageUsingTemplate(
            String to,
            String subject,
            SimpleMailMessage template,
            String... templateArgs)
            throws MailException;

    /**
     * Gửi email có file đính kèm
     * @param to
     * @param subject
     * @param content
     * @param pathToAttachment 
     */
    void sendMessageWithAttachment(
            String to,
            String subject,
            String content,
            String pathToAttachment)
             throws MailException, MessagingException;
}

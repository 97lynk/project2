/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.sendmail.exception;

/**
 *
 * @author 97lynk
 */
public class EmailExistsException extends RuntimeException {

    public EmailExistsException(Throwable throwable) {
        super(throwable);
    }

    public EmailExistsException(String message) {
        super(message);
    }

    public EmailExistsException(Throwable throwable, String message) {
        super(message, throwable);
    }

    public EmailExistsException() {
    }

}

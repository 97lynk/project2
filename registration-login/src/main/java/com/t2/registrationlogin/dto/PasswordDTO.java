/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.registrationlogin.dto;

import com.t2.registrationlogin.annotation.PasswordMatches;
import com.t2.registrationlogin.annotation.ValidPassword;

import java.io.Serializable;

/**
 *
 * @author 97lynk
 */
@PasswordMatches
public class PasswordDTO implements Serializable {

    @ValidPassword
    private String newPassword;

    private String confirmPassword;

    public PasswordDTO() {
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return String.format("Password: [newPassword=%s,confirmPassword=%s]",
                newPassword, confirmPassword);
    }

}

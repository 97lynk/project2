/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.registrationlogin.service;

import com.t2.registrationlogin.entity.*;
import com.t2.registrationlogin.dto.*;
import com.t2.registrationlogin.utils.*;
import com.t2.sendmail.exception.EmailExistsException;

/**
 *
 * @author 97lynk
 */
public interface IUserService {

    User registerNewUserAccount(UserDTO userDTO)
            throws EmailExistsException;

    User getUser(String verificationToken);

    User getUserByEmail(String email);

    VerificationToken getVerificationToken(User user);

    void saveRegisteredUser(User user);

    VerificationToken createVerificationToken(User user);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String existingVerificationToken);

    TokenStatus validateVerificationToken(String token);

    Role getRoleByName(String name);

    Privilege getPrivilegeByName(String name);

    PasswordResetToken createPasswordResetTokenForUser(User user);

    PasswordResetToken generateNewPasswordResetToken(String existingPasswordResetToken);

    TokenStatus validatePasswordResetToken(long userID, String token);

    PasswordResetToken getPasswordResetTokenByUser(User user);

    <T> EmailStatus getStatusForEmail(User user, T token);

}

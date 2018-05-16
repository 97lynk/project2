/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.registrationlogin.annotation;


import com.t2.registrationlogin.dto.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj instanceof UserDTO) {
            UserDTO user = (UserDTO) obj;
            return user.getPassword().equals(user.getMatchingPassword());
        }
        if (obj instanceof PasswordDTO) {
            PasswordDTO password = (PasswordDTO) obj;
            return password.getNewPassword().equals(password.getConfirmPassword());
        }
        return false;
    }
}

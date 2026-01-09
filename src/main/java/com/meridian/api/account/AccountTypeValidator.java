package com.meridian.api.account;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class AccountTypeValidator implements ConstraintValidator<ValidAccountType, AccountType> {

    @Override
    public boolean isValid(AccountType value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        if(Arrays.stream(AccountType.values()).toList().contains(value)) {
            return true;
        }

//        for (AccountType t : AccountType.values()) {
//            if (t == value) {
//                return true;
//            }
//        }

        return false;
    }
}


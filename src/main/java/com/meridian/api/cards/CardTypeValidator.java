package com.meridian.api.cards;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class CardTypeValidator implements ConstraintValidator<ValidCardType, CardType> {

    @Override
    public boolean isValid(CardType value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        if(Arrays.stream(CardType.values()).toList().contains(value)) {
            return true;
        }

        return false;
    }
}

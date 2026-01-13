package com.meridian.api.cards;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardTypeValidator implements ConstraintValidator<ValidCardType, CardType> {

    @Override
    public boolean isValid(CardType value, ConstraintValidatorContext context) {
        // Since the parameter is strongly typed as CardType enum,
        // it will always be a valid enum value if not null
        return true;
    }
}

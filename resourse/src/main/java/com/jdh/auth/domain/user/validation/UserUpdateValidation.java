package com.jdh.auth.domain.user.validation;


import com.jdh.auth.domain.user.User;
import com.jdh.auth.domain.user.UserService;
import com.jdh.auth.domain.user.validation.rules.UniqueEmailRule;
import com.jdh.auth.infra.validation.AppValidatorImpl;

public class UserUpdateValidation extends AppValidatorImpl {

    public UserUpdateValidation(UserService service, User bean) {
        addRule(new UniqueEmailRule(service, bean));
    }
}

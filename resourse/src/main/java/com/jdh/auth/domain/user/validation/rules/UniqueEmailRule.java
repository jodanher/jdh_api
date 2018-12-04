package com.jdh.auth.domain.user.validation.rules;

import com.jdh.auth.domain.user.User;
import com.jdh.auth.domain.user.UserService;
import com.jdh.auth.infra.exceprion.BusinessException;
import com.jdh.auth.infra.validation.BusinessRule;

import java.util.Optional;

public class UniqueEmailRule implements BusinessRule {

    private User bean;

    private Optional<User> userOptional;

    public UniqueEmailRule(UserService service, User bean) {
        this.bean = bean;
        userOptional = service.findOneByEmail(bean.getEmail());
    }

    private boolean isValid() {
        User user = userOptional.get();
        return !bean.isNew() && bean.getId().equals(user.getId());
    }

    @Override
    public void verify(BusinessException ex) {
        if (userOptional.isPresent() && !isValid()) {
            ex.addMessages("E-mail já existente");
        }
    }
}

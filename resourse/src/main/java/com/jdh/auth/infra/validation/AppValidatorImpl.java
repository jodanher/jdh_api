package com.jdh.auth.infra.validation;

import com.jdh.auth.infra.exceprion.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class AppValidatorImpl implements AppValidator {

    private static final Logger LOGGER = LogManager.getLogger(AppValidatorImpl.class);

    private final List<BusinessRule> businessRules = new ArrayList<>();

    protected void addRule(BusinessRule rule) {
        businessRules.add(rule);
    }

    @Override
    public void execute() throws BusinessException {
        BusinessException businessException = new BusinessException();
        businessRules.forEach(b -> b.verify(businessException));

        if (businessException.hasError()) {
            LOGGER.error(businessException.messages());
            throw businessException;
        }
    }
}

package com.jdh.auth.infra.validation;

import com.jdh.auth.infra.exceprion.BusinessException;

public interface BusinessRule {

    void verify(BusinessException ex);
}

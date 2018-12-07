package com.jdh.auth.infra.validation;


import com.jdh.auth.infra.exceprion.BusinessException;

public interface AppValidator {

    void execute() throws BusinessException;
}

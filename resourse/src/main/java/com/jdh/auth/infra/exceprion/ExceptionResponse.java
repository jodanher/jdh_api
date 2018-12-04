package com.jdh.auth.infra.exceprion;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class ExceptionResponse implements Serializable {

    private String message;

    public ExceptionResponse(String message) {
        this.message = message;
    }
}

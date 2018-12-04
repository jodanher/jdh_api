package com.jdh.auth.infra.exceprion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessException extends RuntimeException {

    List<String> messages = new ArrayList<>();

    public void addMessages(String message) {
        messages.add(message);
    }

    public boolean hasError() {
        return !messages.isEmpty();
    }

    public String messages() {
        return messages.stream().collect(Collectors.joining(System.lineSeparator()));
    }
}

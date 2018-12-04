package com.jdh.auth.userinterface.home;

import com.jdh.auth.domain.home.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/oapi")
public interface HomeResource {

    @GetMapping
    Message home();

    @GetMapping("/logout")
    void logout(String auth);

}

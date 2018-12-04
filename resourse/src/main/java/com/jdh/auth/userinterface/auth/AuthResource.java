package com.jdh.auth.userinterface.auth;

import com.jdh.auth.domain.user.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/auth")
public interface AuthResource {

    @PostMapping("/login")
    User login(User user);

    @GetMapping("/user")
    User user(HttpServletRequest request);

    @PostMapping("/valid")
    User valid(String auth);
}

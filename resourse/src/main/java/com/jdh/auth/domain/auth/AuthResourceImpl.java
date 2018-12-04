package com.jdh.auth.domain.auth;

import com.jdh.auth.domain.user.User;
import com.jdh.auth.userinterface.auth.AuthResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthResourceImpl implements AuthResource {

    private AuthService service;

    public AuthResourceImpl(AuthService service) {
        this.service = service;
    }

    @Override
    public User login(@RequestBody User user) {
        return service.login(user.getEmail(), user.getPassword());
    }

    @Override
    public User user(HttpServletRequest request) {
        return service.user(request);
    }

    @Override
    public User valid(@RequestHeader("Authorization") String auth) {
        String token = auth.replace("Bearer ", "");
        return service.validToken(token);
    }
}

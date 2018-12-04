package com.jdh.auth.domain.home;

import com.jdh.auth.userinterface.home.HomeResource;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResourceImpl implements HomeResource {

    private ConsumerTokenServices tokenService;

    public HomeResourceImpl(ConsumerTokenServices tokenService) {
        this.tokenService = tokenService;
    }

    public Message home() {
        return new Message("Hello World");
    }

    public void logout(@RequestHeader("Authorization") String auth) {
        tokenService.revokeToken(auth.replace("Bearer ", ""));
    }

}

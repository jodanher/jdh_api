package com.jdh.auth;

import com.jdh.auth.domain.user.User;
import com.jdh.auth.domain.user.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.jdh.auth")
public class ResourceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(ResourceApplication.class, args);

        User user = new User();
        user.setName("Daniel Herszenhorn");
        user.setEmail("jodanher@gmail.com");
        user.setPassword("123456");
        UserService service = ctx.getBean(UserService.class);
        if (!service.findOneByEmail(user.getEmail()).isPresent()) {
            service.save(user);
        }
    }
}


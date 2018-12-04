package com.jdh.auth.domain.user;

import com.jdh.auth.userinterface.user.UserResource;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
class UserResourceImpl implements UserResource {

    private UserService service;

    public UserResourceImpl(UserService service) {
        this.service = service;
    }

    @Override
    public Iterable<User> get(Sort sort) {
        return service.findAll(sort);
    }

    @Override
    public User get(@PathVariable UUID id) {
        return service.findById(id).orElse(null);
    }

    @Override
    public User post(@RequestBody User user) {
        return service.save(user);
    }

    @Override
    public User put(@PathVariable UUID id, @RequestBody User user) {
        return service.save(user);
    }

    @Override
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}

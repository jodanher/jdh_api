package com.jdh.auth.userinterface.user;

import com.jdh.auth.domain.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/api/users")
public interface UserResource {

    @GetMapping
    Iterable<User> get(Sort sort);

    @GetMapping("/{id}")
    User get(UUID id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    User post(@Valid User user);

    @PutMapping("{id}")
    User put(UUID id, @Valid User user);

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(UUID id);
}

package com.jdh.auth.domain.user;

import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> findOneByEmail(String email);

    Optional<User> findById(UUID id);

    Iterable<User> findAll(Sort sort);

    @Transactional(rollbackOn = Throwable.class)
    User save(User user);

    @Transactional(rollbackOn = Throwable.class)
    void clear();
}

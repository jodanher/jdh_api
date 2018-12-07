package com.jdh.auth.domain.user;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    @Transactional(readOnly = true)
    Optional<User> findOneByEmail(String email);

    @Transactional(readOnly = true)
    Optional<User> findById(UUID id);

    @Transactional(readOnly = true)
    Iterable<User> findAll(Sort sort);

    @Transactional(rollbackFor = Throwable.class)
    User save(User user);

    @Transactional(rollbackFor = Throwable.class)
    void delete(UUID id);

    @Transactional(rollbackFor = Throwable.class)
    void clear();
}

package com.jdh.auth.domain.user;

import com.jdh.auth.domain.auth.AuthService;
import com.jdh.auth.domain.user.validation.UserInserValidation;
import com.jdh.auth.domain.user.validation.UserUpdateValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
class UserServiceImpl implements UserService {

    private UserRepository repository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findOneByEmail(String email) {
        return repository.findOneByEmail(email);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<User> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            new UserInserValidation(this, user).execute();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user = repository.save(user);
            String token = authService.generateToken(user);
            user.setToken(token);
        } else {
            new UserUpdateValidation(this, user).execute();
            String oldPassword = repository.findOnePasswordById(user.getId());
            if (!oldPassword.equals(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }

        return repository.save(user);
    }

    @Override
    public void clear() {
        repository.deleteAll();
    }
}

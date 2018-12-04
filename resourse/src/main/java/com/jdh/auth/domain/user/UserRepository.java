package com.jdh.auth.domain.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, UUID> {

    Optional<User> findOneByEmail(String email);

    @Query("select u.password from User u where u.id = ?1")
    String findOnePasswordById(UUID id);

    @Query("select u.password from User u where u.email = ?1")
    String findOnePasswordByEmailAndId(String email);

    @Query("select u.password as password, u.name as name from User u where u.email = ?1")
    Tuple findOnePasswordAndNameByEmail(String email);
}
package com.oauth.repositories;

/**
 * Created by ashish.p on 31/7/17.
 */

import com.oauth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByVerifyToken(String verifyToken);
}

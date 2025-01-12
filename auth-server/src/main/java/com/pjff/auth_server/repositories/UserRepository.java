package com.pjff.auth_server.repositories;

import com.pjff.auth_server.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Vid 103
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //Vid 107
    Optional<UserEntity> findByUsername(String username);
}


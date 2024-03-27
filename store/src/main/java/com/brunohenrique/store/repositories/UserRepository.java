package com.brunohenrique.store.repositories;

import com.brunohenrique.store.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByDocument(String document);
}

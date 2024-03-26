package com.brunohenrique.store.repository;

import com.brunohenrique.store.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}

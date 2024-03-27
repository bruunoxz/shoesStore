package com.brunohenrique.store.repositories;

import com.brunohenrique.store.domain.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoeRepository extends JpaRepository<Shoe, String> {
    Optional<Shoe> findByName(String name);
}

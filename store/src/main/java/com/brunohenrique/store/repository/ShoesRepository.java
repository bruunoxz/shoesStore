package com.brunohenrique.store.repository;

import com.brunohenrique.store.domain.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoesRepository extends JpaRepository<Shoes, String> {
}

package com.brunohenrique.store.repository;

import com.brunohenrique.store.domain.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoeRepository extends JpaRepository<Shoe, String> {
}

package com.brunohenrique.store.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "shoes")
@Table(name = "shoes")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
public class Shoes {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    private String name;

    private String description;

    private Double price;
}

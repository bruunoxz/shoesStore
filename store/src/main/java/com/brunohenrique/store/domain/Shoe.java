package com.brunohenrique.store.domain;

import com.brunohenrique.store.dtos.RequestShoe;
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
public class Shoe {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    private String name;

    private String description;

    private Double price;

    public Shoe(RequestShoe requestShoe){
        this.name = requestShoe.name();
        this.description = requestShoe.description();
        this.price = requestShoe.price();
    }
}

package com.brunohenrique.store.domain;

import com.brunohenrique.store.dtos.RequestShoe;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity(name = "shoes")
@Table(name = "shoes")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shoe {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    private String name;

    @Column(length = 1000)
    private String description;

    @NotNull
    private Double price;

    public Shoe(RequestShoe requestShoe){
        this.name = requestShoe.name();
        this.description = requestShoe.description();
        this.price = requestShoe.price();
    }
}

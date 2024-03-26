package com.brunohenrique.store.domain;

import com.brunohenrique.store.dtos.RequestUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(of = "id")
@Entity(name="users")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Column(unique = true) @NotNull
    private String email;

    @NotNull
    private String password;

    @Column(unique = true)
    private String document;

    public User(RequestUser requestUser){
        this.name = requestUser.name();
        this.email = requestUser.email();
        this.password = requestUser.password();
        this.document = requestUser.document();
    }
}

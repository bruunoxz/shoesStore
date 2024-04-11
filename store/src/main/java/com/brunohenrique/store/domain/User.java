package com.brunohenrique.store.domain;

import com.brunohenrique.store.dtos.RequestUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@EqualsAndHashCode(of = "id")
@Entity(name="users")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Column(unique = true) @NotNull
    private String email;

    @NotNull
    private String password;

    @Column(unique = true)
    private String document;

    private String verificationCode;

    private boolean enabled;

    public User(RequestUser requestUser){
        this.name = requestUser.name();
        this.email = requestUser.email();
        this.password = requestUser.password();
        this.document = requestUser.document();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled(){
        return this.enabled;
    }
}

package com.brunohenrique.store.dtos;

import jakarta.validation.constraints.NotBlank;

public record RequestUser(String id, @NotBlank String name, @NotBlank String email, @NotBlank String password, @NotBlank String document) {
}

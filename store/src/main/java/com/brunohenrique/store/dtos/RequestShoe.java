package com.brunohenrique.store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestShoe(String id, @NotBlank String name, @NotBlank String description, @NotBlank String brand, @NotNull Double price) {
}

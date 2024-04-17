package com.brunohenrique.store.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestUser(
        String id,

        @NotBlank(message = "O nome não pode ficar em branco")
        @NotNull(message = "O nome não pode ser nulo")
        String name,

        @NotBlank(message = "O email não pode ficar em branco")
        @NotNull(message = "O email não pode ser nulo")
        @Email
        String email,

        @NotBlank(message = "A senha não pode ficar em branco")
        @NotNull(message = "A senha não pode ser nula")
        @Size(min = 8, message = "A senha deve conter no mínimo 8 caracteres")
        String password,

        @NotBlank(message = "O documento não pode ficar em branco")
        @NotNull(message = "O documento não pode ser nulo")
        String document
){
}

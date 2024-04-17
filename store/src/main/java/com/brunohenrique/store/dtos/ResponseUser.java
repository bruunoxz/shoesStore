package com.brunohenrique.store.dtos;

public record ResponseUser(String id, String name, String email, String password, String document) {
    public ResponseUser(String id, String name, String email, String password, String document) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.document = document;
    }
}

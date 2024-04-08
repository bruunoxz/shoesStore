package com.brunohenrique.store.controllers;

import com.brunohenrique.store.domain.User;
import com.brunohenrique.store.dtos.RequestUser;
import com.brunohenrique.store.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){return ResponseEntity.ok(userService.listAllUsers());}

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){return ResponseEntity.ok(userService.listUserById(id));}

    @PostMapping
    public ResponseEntity registerUser(@RequestBody @Valid RequestUser data){
        userService.createUser(data);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody @Valid RequestUser data){return ResponseEntity.ok(userService.updateUser(data));}

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

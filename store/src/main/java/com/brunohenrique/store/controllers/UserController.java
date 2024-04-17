package com.brunohenrique.store.controllers;

import com.brunohenrique.store.domain.User;
import com.brunohenrique.store.dtos.RequestUser;
import com.brunohenrique.store.dtos.ResponseUser;
import com.brunohenrique.store.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){return ResponseEntity.ok(userService.listAllUsers());}

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){return ResponseEntity.ok(userService.listUserById(id));}

    @GetMapping("/verify")
    public String verifyUser(@Param("code")String code){
        if(userService.verify(code)){
            return "verify_sucess";
        }else{
            return "verify_fail";
        }
    }


    @PostMapping
    public ResponseEntity<ResponseUser> registerUser(@RequestBody @Valid RequestUser data) throws MessagingException, UnsupportedEncodingException {
        User user = new User(data);
        ResponseUser responseUser = userService.createUser(user);
        return ResponseEntity.ok().body(responseUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody @Valid RequestUser data){return ResponseEntity.ok(userService.updateUser(data));}

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

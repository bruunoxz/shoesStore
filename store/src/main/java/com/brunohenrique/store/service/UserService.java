package com.brunohenrique.store.service;

import com.brunohenrique.store.domain.User;
import com.brunohenrique.store.dtos.RequestUser;
import com.brunohenrique.store.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> listAllUsers(){
        return userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }

    public Optional<User> listUserById(String id){
        return userRepository.findById(id);
    }

    public void createUser(RequestUser data){
        User newUser = new User(data);
        userRepository.save(newUser);
    }

    public User update(RequestUser data){
        Optional<User> optionalUser = userRepository.findById(data.id());
        if(optionalUser.isPresent()){
            User newUser = optionalUser.get();
            newUser.setName(data.name());
            newUser.setEmail(data.email());
            newUser.setPassword(data.password());
            newUser.setDocument(data.document());
            userRepository.save(newUser);
            return newUser;
        }else{
            throw new EntityNotFoundException();
        }
    }

    public void delete(String id){userRepository.deleteById(id);}
}

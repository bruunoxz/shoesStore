package com.brunohenrique.store.services;

import com.brunohenrique.store.domain.User;
import com.brunohenrique.store.dtos.RequestUser;
import com.brunohenrique.store.exceptions.DataAlreadyRegistered;
import com.brunohenrique.store.exceptions.DataNotFoundException;
import com.brunohenrique.store.exceptions.InputValidationException;
import com.brunohenrique.store.repositories.UserRepository;
import com.brunohenrique.store.util.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> listAllUsers(){
        return userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }

    public User listUserById(String id){
        return userRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Usuário não encontrado"));
    }

        public void createUser(User user){
            if(!(user.getEmail().contains("@"))){
                throw new InputValidationException("É necessário inserir @ no campo de email");
            }else if(userRepository.findByEmail(user.getEmail()).isPresent()){
                throw new DataAlreadyRegistered("Email já registrado");
            }else if(userRepository.findByDocument(user.getDocument()).isPresent()) {
                throw new DataAlreadyRegistered("CPF já registrado");
            }else{
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);

                String randomCode = RandomString.generateRandomString(64);
                user.setVerificationCode(randomCode);
                user.setEnabled(false);
                userRepository.save(user);
            }
        }

    public User updateUser(RequestUser data){
        return userRepository.findById(data.id())
                .map(user -> {
                    user.setName(data.name());
                    user.setEmail(data.email());
                    user.setPassword(data.password());
                    user.setDocument(data.document());
                    return userRepository.save(user);
                })
                .orElseThrow(()->new DataNotFoundException("Usuário não encontrado"));
    }

    public void deleteUser(String id){userRepository.deleteById(id);}
}

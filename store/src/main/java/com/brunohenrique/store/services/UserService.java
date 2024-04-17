package com.brunohenrique.store.services;

import com.brunohenrique.store.domain.User;
import com.brunohenrique.store.dtos.RequestUser;
import com.brunohenrique.store.dtos.ResponseUser;
import com.brunohenrique.store.exceptions.DataAlreadyRegistered;
import com.brunohenrique.store.exceptions.DataNotFoundException;
import com.brunohenrique.store.repositories.UserRepository;
import com.brunohenrique.store.util.RandomString;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    public List<User> listAllUsers(){
        return userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }

    public User listUserById(String id){
        return userRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Usuário não encontrado"));
    }

        public ResponseUser createUser(User user) throws MessagingException, UnsupportedEncodingException {
            if(userRepository.findByEmail(user.getEmail()).isPresent()){
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
                mailService.sendVerificationEmail(user);
                return new ResponseUser(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getDocument());
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

    public boolean verify(String verificationCode){
        User user = userRepository.findByVerificationCode(verificationCode);

        if(user == null || user.isEnabled()){
            return false;
        }else{
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }
    }
}

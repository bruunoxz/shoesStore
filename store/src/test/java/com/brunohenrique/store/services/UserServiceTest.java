package com.brunohenrique.store.services;

import com.brunohenrique.store.domain.User;
import com.brunohenrique.store.dtos.RequestUser;
import com.brunohenrique.store.exceptions.DataAlreadyRegistered;
import com.brunohenrique.store.exceptions.DataNotFoundException;
import com.brunohenrique.store.exceptions.InputValidationException;
import com.brunohenrique.store.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should list users sucessfully")
    void listAllUsersCase1() {
        List<User> listUsers = new ArrayList<>();
        listUsers.add(new User("123e4567-e89b-12d3-a456-426655440000","Agnaldo Souza", "agnaldosouza@gmail.com", "12345", "99999999901"));
        listUsers.add(new User("123e4567-e89b-12d3-a456-426655440012", "Debora Martins", "deboramartins@gmail.com", "12345", "11111111198"));

        when(userRepository.findAll()).thenReturn(listUsers);

        List<User> expected = listUsers.stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
        assertEquals(expected, userService.listAllUsers());
    }

    @Test
    @DisplayName("Should throw Exception when failure repository")
    void listAllUsersCase2() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("Falha ao buscar usuários no repositório"));

        assertThrows(RuntimeException.class, () -> userService.listAllUsers());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should list user by id sucessfully")
    void listUserByIdCase1() {
        String userId = "123e4567-e89b-12d3-a456-426655440000";
        
        User expectedUser = new User(userId, "Luis Artur", "luisartur@gmail.com", "12345", "99999999901");
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        User result = userService.listUserById(userId);

        verify(userRepository, times(1)).findById(userId);
        
        assertEquals(expectedUser, result);
    }

    @Test
    @DisplayName("Should throw Exception when user not found")
    void listUserByIdCase2(){
        String id = "123e4567-e89b-12d3-a456-426655440000";

        when(userRepository.findById(id)).thenThrow(new DataNotFoundException("Usuário não encontrado"));

        assertThrows(DataNotFoundException.class, () -> userService.listUserById(id));

        verify(userRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should throw Exception when failure repository")
    void listUserByIdCase3(){
        String id = "123e4567-e89b-12d3-a456-426655440000";

        when(userRepository.findById(id)).thenThrow(new RuntimeException("Falha ao buscar o usuário no repositório"));

        assertThrows(RuntimeException.class, () -> userService.listUserById(id));

        verify(userRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should create user sucessfully")
    void createUserCase1() {
        RequestUser userDTO = new RequestUser("123e4567-e89b-12d3-a456-426655440000","Luis Artur", "luisartur@gmail.com", "12345", "99999999901");

        when(userRepository.findByEmail(userDTO.email())).thenReturn(Optional.empty());
        when(userRepository.findByDocument(userDTO.document())).thenReturn(Optional.empty());

        userService.createUser(userDTO);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw Exception when email not contains the character @")
    void createUserCase2(){
        RequestUser userDTO = new RequestUser("123e4567-e89b-12d3-a456-426655440000","Luis Artur", "emailinvalido.com", "12345", "99999999901");

        assertThrows(InputValidationException.class, () -> userService.createUser(userDTO));
    }

    @Test
    @DisplayName("Should throw Exception when email already registered")
    void createUserCase3(){
        RequestUser userDTO = new RequestUser("123e4567-e89b-12d3-a456-426655440000","Luis Artur", "luisartur@gmail.com", "12345", "99999999901");

        when(userRepository.findByEmail(userDTO.email())).thenThrow(new DataAlreadyRegistered("Email já registrado"));

        assertThrows(DataAlreadyRegistered.class, () -> userService.createUser(userDTO));
    }

    @Test
    @DisplayName("Should throw Exception when document already registered")
    void createUserCase4(){
        RequestUser userDTO = new RequestUser("123e4567-e89b-12d3-a456-426655440000","Luis Artur", "luisartur@gmail.com", "12345", "99999999901");

        when(userRepository.findByDocument(userDTO.document())).thenThrow(new DataAlreadyRegistered("CPF já registrado"));

        assertThrows(DataAlreadyRegistered.class, () -> userService.createUser(userDTO));
    }

    @Test
    @DisplayName("Should throw Exception when failure repository")
    void createUserCase5(){
        RequestUser userDTO = new RequestUser("123e4567-e89b-12d3-a456-426655440000","Luis Artur", "luisartur@gmail.com", "12345", "99999999901");
        User userTest = new User(userDTO);

        when(userRepository.save(userTest)).thenThrow(new RuntimeException("Falha ao criar o usuário no repositório"));

        assertThrows(RuntimeException.class, () -> userService.createUser(userDTO));

        verify(userRepository, times(1)).save(userTest);
    }

    @Test
    @DisplayName("Should update user sucessfully")
    void updateUserCase1() {
        RequestUser userDTO = new RequestUser("123e4567-e89b-12d3-a456-426655440000","Luis Artur", "luisartur@gmail.com", "12345", "99999999901");
        User oldUser = new User("123e4567-e89b-12d3-a456-426655440000", "Luis Arthur", "luisartur@gmail.com", "54321", "99999999901");
        User newUser = new User(userDTO);

        when(userRepository.findById(userDTO.id())).thenReturn(Optional.of(oldUser));
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User result = userService.updateUser(userDTO);

        verify(userRepository, times(1)).findById(userDTO.id());
        verify(userRepository, times(1)).save(any(User.class));

        assertEquals(newUser, result);
    }

    @Test
    @DisplayName("Should throw Exception when user not found")
    void updateUserCase2(){
        RequestUser userDTO = new RequestUser("123e4567-e89b-12d3-a456-426655440000","Luis Artur", "luisartur@gmail.com", "12345", "99999999901");

        when(userRepository.findById(userDTO.id())).thenThrow(new DataNotFoundException("Usuário não encontrado"));

        assertThrows(DataNotFoundException.class, () -> userService.updateUser(userDTO));
    }

    @Test
    @DisplayName("Should throw Exception when failure repository")
    void updateUserCase3(){
        String id = "123e4567-e89b-12d3-a456-426655440000";

        when(userRepository.findById(id)).thenThrow(new RuntimeException("Falha ao buscar o usuário no repositório"));

        assertThrows(RuntimeException.class, () -> userService.listUserById(id));

        verify(userRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should delete user sucessfully")
    void deleteUserCase1() {
        User userTest = new User("123e4567-e89b-12d3-a456-426655440000", "Roberto Marinho", "robertomarinho@gmail.com", "12345", "99999999901");

        userService.deleteUser(userTest.getId());

        verify(userRepository, times(1)).deleteById(userTest.getId());
    }

    @Test
    @DisplayName("Should throw Exception when failure repository")
    void deleteUserCase2(){
        String id = "123e4567-e89b-12d3-a456-426655440000";
        doThrow(new RuntimeException("Falha ao deletar o usuário do repositório")).when(userRepository).deleteById(id);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(id));

        verify(userRepository, times(1)).deleteById(id);
    }
}
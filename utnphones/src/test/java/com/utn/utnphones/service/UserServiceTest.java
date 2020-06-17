package com.utn.utnphones.service;

import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.UserRole;
import com.utn.utnphones.model.enums.UserStatus;
import com.utn.utnphones.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {


    UserService userService;
    @Mock
    UserRepository userRepository;

    @Before
    public void setUp() {
        initMocks(this);
        this.userService = new UserService(userRepository);
    }

    @Test
    public void getAllOk() {
        User u1 = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        User u2 = new User(2, "user2", "pass2", "mailfalso@hotmail.com", "name2", "lastname2", 40020328, null, "calle falsa 123", UserRole.CLIENT, UserStatus.ACTIVE, null);

        List<User> userList = new ArrayList<>();
        userList.add(u1);
        userList.add(u2);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> userList2 = userService.getAll();

        assertEquals(2, userList2.size());
        assertEquals(userList, userList2);
    }

    @Test
    public void getAllEmpty() {
        List<User> userList = new ArrayList<>();

        when(userRepository.findAll()).thenReturn(userList);

        List<User> userList2 = userService.getAll();

        assertEquals(0, userList2.size());
        assertEquals(userList, userList2);
    }



    @Test
    public void login() {
    }

    @Test
    public void addUser() {
    }

    @Test
    public void getUserById() {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);

        Mockito.when(userRepository.findById(1)).thenReturn(java.util.Optional.of(expected));
        Optional<User> retrurned = userRepository.findById(1);

        assertEquals(expected,retrurned.get());
    }

    @Test
    public void getUserByUsernameAndPassword() {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);

        Mockito.when(userRepository.findByUsernameAndPassword("user1","pass")).thenReturn(java.util.Optional.of(expected));
        Optional<User> retrurned = userRepository.findByUsernameAndPassword("user1","pass");

        assertEquals(expected,retrurned.get());
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void existsByIdTrue() {
        Mockito.when(userRepository.existsById(1)).thenReturn(true);
        boolean returned = userRepository.existsById(1);
        assertEquals(true,returned);
    }
    @Test
    public void existsByIdFalse() {
        Mockito.when(userRepository.existsById(233)).thenReturn(false);
        boolean returned = userRepository.existsById(233);
        assertEquals(false,returned);
    }

    @Test
    public void getJWTToken() {
    }

}
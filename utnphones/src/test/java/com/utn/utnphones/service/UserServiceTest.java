package com.utn.utnphones.service;

import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.UserRole;
import com.utn.utnphones.model.enums.UserStatus;
import com.utn.utnphones.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

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
        User u1 = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, null, UserStatus.ACTIVE);
        User u2 = new User(2, "user2", "pass2", "mailfalso@hotmail.com", "name2", "lastname2", 40020328, null, "calle falsa 123", UserRole.CLIENT, null, UserStatus.ACTIVE);

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
}
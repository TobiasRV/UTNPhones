package com.utn.utnphones.service;

import com.utn.utnphones.controller.UserController;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.model.City;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.UserRole;
import com.utn.utnphones.model.enums.UserStatus;
import com.utn.utnphones.repository.CityRepository;
import com.utn.utnphones.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

    private UserService service;

    @Mock
    UserRepository userRepository;
    @Mock
    CityRepository cityRepository;

    @Before
    public void setUP(){
        
    }

    @Test
    public void login() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void addUser() {
    }

    @Test
    public void testGetUserById() throws UserNotFoundException {
        int id = 1;
        User expected = new User(id,"username","","name","surename",28623956,new City(), UserRole.CLIENT,new ArrayList<Line>(), UserStatus.ACTIVE);
        when(service.getUserById(id)).thenReturn(expected);
        User returnedUser = service.getUserById(id);
        assertEquals(expected.getDni(),returnedUser.getDni());
        verify(service,times(1)).getUserById(id);
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void updateUser() {
    }
}
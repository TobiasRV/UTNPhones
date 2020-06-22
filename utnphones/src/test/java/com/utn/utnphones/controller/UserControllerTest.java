package com.utn.utnphones.controller;

import com.utn.utnphones.dto.CallQueryReturnDto;
import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.exceptions.InvalidLoginException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.City;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.UserRole;
import com.utn.utnphones.model.enums.UserStatus;
import com.utn.utnphones.security.SessionManager;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserControllerTest {

    UserController userController;
    @Mock
    UserService userService;
    @Mock
    CityService cityService;
    @Mock
    SessionManager sessionManager;


    @Before
    public void setUp() {
        initMocks(this);
        userController = new UserController(userService, cityService, sessionManager);
    }


    @Test
    public void getAll() {
        User u1 = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        User u2 = new User(2, "user2", "pass2", "mailfalso@hotmail.com", "name2", "lastname2", 40020328, null, "calle falsa 123", UserRole.CLIENT, UserStatus.ACTIVE, null);

        List<User> expected = new ArrayList<>();
        expected.add(u1);
        expected.add(u2);

        when(userService.getAll()).thenReturn(expected);

        ResponseEntity<List<User>> returned = userController.getAll();


        assertNotNull(returned);
        assertThat(returned.getBody().size(), is(2));
        assertEquals(expected, returned.getBody());
    }

    @Test
    public void getAllEmpty() {

        List<User> expected = new ArrayList<>();

        when(userService.getAll()).thenReturn(expected);

        ResponseEntity<List<User>> returned = userController.getAll();

        assertNotNull(returned);
        assertEquals(HttpStatus.NO_CONTENT,returned.getStatusCode());
    }

    @Test
    public void getUserById() throws UserNotFoundException {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);

        Mockito.when(userService.getUserById(1)).thenReturn(expected);

        User returned = userService.getUserById(1);

        assertNotNull(returned);
        assertEquals(expected,returned);
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserByIdError() throws UserNotFoundException {
        Mockito.when(userService.getUserById(134)).thenThrow(new UserNotFoundException());
        User returned = userService.getUserById(134);
    }

    @Test
    public void deleteUser() throws UserNotFoundException {
        ResponseEntity returned = userController.deleteUser(1);
        assertNotNull(returned);
        assertEquals(HttpStatus.OK, returned.getStatusCode());

    }

    @After
    public void tearDown() throws Exception {
    }


}
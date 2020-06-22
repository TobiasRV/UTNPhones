package com.utn.utnphones.controller;

import com.utn.utnphones.dto.CallQueryReturnDto;
import com.utn.utnphones.exceptions.UserNotFoundException;
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
    public void getAllOk() {
        User u1 = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        User u2 = new User(2, "user2", "pass2", "mailfalso@hotmail.com", "name2", "lastname2", 40020328, null, "calle falsa 123", UserRole.CLIENT, UserStatus.ACTIVE, null);

        List<User> userList = new ArrayList<>();
        userList.add(u1);
        userList.add(u2);

        when(userService.getAll()).thenReturn(userList);

        List<User> userList2 = userController.getAll().getBody();


        assertNotNull(userList2);
        assertThat(userList2.size(), is(2));
        assertEquals(userList2, userList);
    }

    @Test
    public void getAllEmpty() {
        HttpStatus response = null;
        List<User> expected = new ArrayList<>();

        when(userService.getAll()).thenReturn(expected);

        List<User> returned = userService.getAll();

        if (returned.size() == 0) {
            response = HttpStatus.NO_CONTENT;
        }
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response);
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

    @After
    public void tearDown() throws Exception {
    }


}
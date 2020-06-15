package com.utn.utnphones.controller;

import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.UserRole;
import com.utn.utnphones.model.enums.UserStatus;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserControllerTest {

    UserController userController;
    @Mock
    UserService userService;
    @Mock
    CityService cityService;

    @Before
    public void setUp() {
        initMocks(this);
        userController = new UserController(userService, cityService);
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

        //userlist.size must be 2
        if (userList2 != null) {
            assertThat(userList2.size(), is(2));
        }

        //check if userlist contains the same elements
        assertEquals(userList2, userList);
    }

    @Test
    public void getAllEmpty() {
        List<User> userList = new ArrayList<>();

        when(userService.getAll()).thenReturn(userList);

        HttpStatus status = userController.getAll().getStatusCode();

        //We spect NO_CONTENT status
        assertEquals(HttpStatus.NO_CONTENT, status);
    }


}
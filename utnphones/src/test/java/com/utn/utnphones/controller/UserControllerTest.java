package com.utn.utnphones.controller;

import com.utn.utnphones.dto.LongestCallDto;
import com.utn.utnphones.exceptions.LineNotFoundException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.LineStatus;
import com.utn.utnphones.model.enums.LineType;
import com.utn.utnphones.model.enums.UserRole;
import com.utn.utnphones.model.enums.UserStatus;
import com.utn.utnphones.service.CallService;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.LineService;
import com.utn.utnphones.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    @Mock
    LineService lineService;
    @Mock
    CallService callService;


    @Before
    public void setUp() {
        initMocks(this);
        userController = new UserController(userService, cityService, lineService, callService);
    }

    @Test
    public void getAllOk() {
        User u1 = new User(1, "user1", "pass", "name1", "lastname1", 40020327, null, UserRole.CLIENT, null, UserStatus.ACTIVE);
        User u2 = new User(2, "user2", "pass2", "name2", "lastname2", 40020328, null, UserRole.CLIENT, null, UserStatus.ACTIVE);

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


    @Test
    public void getUserByLongestCallOk() throws LineNotFoundException, UserNotFoundException {
        User u = new User(1, "user1", "pass", "name1", "lastname1", 40020327, null, UserRole.CLIENT, null, UserStatus.ACTIVE);
        Line line = new Line(1, u, null, "6363325", LineType.MOBILE, LineStatus.ACTIVE);
        Call call = new Call(1, line, null, null, null, 600, 500.0, 50.0);
        LongestCallDto longestCallDto = new LongestCallDto(u, 600);

        when(callService.getLongestCall()).thenReturn(call);
        when(lineService.getLineById(1)).thenReturn(line);
        when(userService.getUserById(1)).thenReturn(u);

        ResponseEntity responseEntity = userController.getUserByLongestCall();

        assertEquals(responseEntity.getBody(), longestCallDto);

    }

    @Test
    public void getUserByLongestCallCallNotFound() throws LineNotFoundException, UserNotFoundException {
        Call call = null;
        when(callService.getLongestCall()).thenReturn(call);
        ResponseEntity responseEntity = userController.getUserByLongestCall();
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
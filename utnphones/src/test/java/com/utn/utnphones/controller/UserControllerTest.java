package com.utn.utnphones.controller;

import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class UserControllerTest {

    private UserController userController;

    @Mock
    UserService userService;
    @Mock
    CityService cityService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getUserById() {
    }
}
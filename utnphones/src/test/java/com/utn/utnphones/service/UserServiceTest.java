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
        User u1 = new User(1, "user1", "pass", "name1", "lastname1", 40020327, null, UserRole.CLIENT, null, UserStatus.ACTIVE);
        User u2 = new User(2, "user2", "pass2", "name2", "lastname2", 40020328, null, UserRole.CLIENT, null, UserStatus.ACTIVE);

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
    public void getUsersByDniEven(){
        User u1 = new User(1, "user1", "pass", "name1", "lastname1", 40020322, null, UserRole.CLIENT, null, UserStatus.ACTIVE);
        User u2 = new User(2, "user2", "pass2", "name2", "lastname2", 40020324, null, UserRole.CLIENT, null, UserStatus.ACTIVE);

        List<User> lu = new ArrayList<>();
        lu.add(u1);
        lu.add(u2);

        when(userRepository.getUserByEvenDni()).thenReturn(lu);

        List<User> response = userRepository.getUserByEvenDni();
        assertNotNull(response);
        assertEquals(lu,response);
    }

    @Test
    public void getUsersByDniOdd(){

        User u1 = new User(1, "user1", "pass", "name1", "lastname1", 40020323, null, UserRole.CLIENT, null, UserStatus.ACTIVE);
        User u2 = new User(2, "user2", "pass2", "name2", "lastname2", 40020325, null, UserRole.CLIENT, null, UserStatus.ACTIVE);

        List<User> lu = new ArrayList<>();
        lu.add(u1);
        lu.add(u2);

        when(userRepository.getUserByOddDni()).thenReturn(lu);

        List<User> response = userRepository.getUserByOddDni();
        assertNotNull(response);
        assertEquals(lu,response);
    }


}
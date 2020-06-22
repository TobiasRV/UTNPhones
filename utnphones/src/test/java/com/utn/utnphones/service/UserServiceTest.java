package com.utn.utnphones.service;

import com.utn.utnphones.exceptions.InvalidLoginException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.UserRole;
import com.utn.utnphones.model.enums.UserStatus;
import com.utn.utnphones.repository.UserRepository;
import org.junit.After;
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



    public void loginError() {

    }

    @Test(expected = ValidationException.class)
    public void addUserUsernameTaken() throws ValidationException {
        User expected = new User(1, "username", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.existsByUsername(expected.getUsername())).thenReturn(false);

        if (!userRepository.existsByUsername(expected.getUsername())){
            throw new ValidationException("username taken");
        }

    }

    @Test(expected = ValidationException.class)
    public void addUserDniTaken() throws ValidationException {
        User expected = new User(1, "username", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.existsByDni(expected.getDni())).thenReturn(false);

        if (!userRepository.existsByDni(expected.getDni())){
            throw new ValidationException("dni taken");
        }

    }

    @Test(expected = ValidationException.class)
    public void addUserEmailTaken() throws ValidationException {
        User expected = new User(1, "username", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.existsByEmail(expected.getEmail())).thenReturn(false);

        if (!userRepository.existsByEmail(expected.getEmail())){
            throw new ValidationException("email taken");
        }

    }

    @Test
    public void getUserById() {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);

        Mockito.when(userRepository.findById(1)).thenReturn(java.util.Optional.of(expected));
        Optional<User> retrurned = userRepository.findById(1);

        assertEquals(expected,retrurned.get());
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserByIdError() throws UserNotFoundException {
        Mockito.when(userRepository.findById(1).orElseThrow(UserNotFoundException::new)).thenThrow(new UserNotFoundException());
        userRepository.findById(1);
    }

    @Test
    public void getUserByUsernameAndPassword() {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);

        Mockito.when(userRepository.findByUsernameAndPassword("user1","pass")).thenReturn(java.util.Optional.of(expected));
        Optional<User> retrurned = userRepository.findByUsernameAndPassword("user1","pass");

        assertEquals(expected,retrurned.get());
    }

    @Test(expected = InvalidLoginException.class)
    public void getUserByUsernameAndPasswordError() throws InvalidLoginException {
        Mockito.when(userRepository.findByUsernameAndPassword("username","password").orElseThrow(InvalidLoginException::new)).thenThrow(new InvalidLoginException());
        userRepository.findByUsernameAndPassword("username","password");
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteUserError() throws UserNotFoundException {
        Mockito.when(userRepository.findById(1).orElseThrow(UserNotFoundException::new)).thenThrow(new UserNotFoundException());
        userRepository.findById(1);
    }

    @Test(expected = UserNotFoundException.class)
    public void updateUserError() throws UserNotFoundException {
        Mockito.when(userRepository.findById(1).orElseThrow(UserNotFoundException::new)).thenThrow(new UserNotFoundException());
        userRepository.findById(1);
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

    @After
    public void tearDown() throws Exception {
    }
}
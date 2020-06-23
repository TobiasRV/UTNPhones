package com.utn.utnphones.service;

import com.utn.utnphones.dto.UpdateUserDto;
import com.utn.utnphones.exceptions.InvalidLoginException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.City;
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

import static org.junit.Assert.assertEquals;
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
    public void login() throws UserNotFoundException {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.getByUsernamePassword("user1","pass")).thenReturn(expected);
        User returned = userService.login("user1","pass");
        assertEquals(expected,returned);
    }


    @Test
    public void addUser() throws ValidationException {
        User expected = new User(1, "username", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.existsByUsername("username")).thenReturn(false);
        Mockito.when(userRepository.existsByDni(40020327)).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("soldanochristian@hotmail.com")).thenReturn(false);
        Mockito.when(userRepository.save(expected)).thenReturn(expected);
        User returned = userService.addUser(expected);
        assertEquals(expected,returned);
    }

    @Test(expected = ValidationException.class)
    public void addUserUsernameTaken() throws ValidationException {
        User expected = new User(1, "username", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.existsByUsername(expected.getUsername())).thenReturn(true);
        userService.addUser(expected);
    }

    @Test(expected = ValidationException.class)
    public void addUserDniTaken() throws ValidationException {
        User expected = new User(1, "username", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.existsByDni(expected.getDni())).thenReturn(true);
        userService.addUser(expected);
    }

    @Test(expected = ValidationException.class)
    public void addUserEmailTaken() throws ValidationException {
        User expected = new User(1, "username", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.existsByEmail(expected.getEmail())).thenReturn(true);
        userService.addUser(expected);
    }

    @Test
    public void getUserById() throws UserNotFoundException {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);

        when(userRepository.findById(1)).thenReturn(Optional.of(expected));
        User returned = userService.getUserById(1);

        assertEquals(expected,returned);
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserByIdError() throws UserNotFoundException {
        when(userRepository.findById(1).orElseThrow(UserNotFoundException::new)).thenThrow(new UserNotFoundException());
        userService.getUserById(1);
    }

    @Test
    public void getUserByUsernameAndPassword() throws InvalidLoginException {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);

        when(userRepository.findByUsernameAndPassword("user1","pass")).thenReturn(Optional.of(expected));
        User retrurned = userService.getUserByUsernameAndPassword("user1","pass");

        assertEquals(expected,retrurned);
    }

    @Test(expected = InvalidLoginException.class)
    public void getUserByUsernameAndPasswordError() throws InvalidLoginException {
        when(userRepository.findByUsernameAndPassword("username","password").orElseThrow(InvalidLoginException::new)).thenThrow(new InvalidLoginException());
        userService.getUserByUsernameAndPassword("username","password");
    }

    @Test
    public void deleteUser() throws UserNotFoundException {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(expected));
        userService.deleteUser(1);
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteUserError() throws UserNotFoundException {
        when(userRepository.findById(1).orElseThrow(UserNotFoundException::new)).thenThrow(new UserNotFoundException());
        userService.deleteUser(1);
    }

    @Test
    public void updateUser() throws UserNotFoundException {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(expected));
        UpdateUserDto updateUserDto = new UpdateUserDto("username","password","user","asd",2230202, new City(),UserRole.CLIENT,UserStatus.ACTIVE);
        userService.updateUser(1,updateUserDto);
    }

    @Test
    public void updateUserUsernameNull() throws UserNotFoundException {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(expected));
        UpdateUserDto updateUserDto = new UpdateUserDto(null,"password","user","asd",2230202, new City(),UserRole.CLIENT,UserStatus.ACTIVE);
        userService.updateUser(1,updateUserDto);
    }

    @Test
    public void updateUserPasswordNull() throws UserNotFoundException {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(expected));
        UpdateUserDto updateUserDto = new UpdateUserDto("username",null,"user","asd",2230202, new City(),UserRole.CLIENT,UserStatus.ACTIVE);
        userService.updateUser(1,updateUserDto);
    }

    @Test
    public void updateUserName() throws UserNotFoundException {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(expected));
        UpdateUserDto updateUserDto = new UpdateUserDto("username","password",null,"asd",2230202, new City(),UserRole.CLIENT,UserStatus.ACTIVE);
        userService.updateUser(1,updateUserDto);
    }

    @Test
    public void updateUserLastNameNull() throws UserNotFoundException {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(expected));
        UpdateUserDto updateUserDto = new UpdateUserDto("username","password","user",null,2230202, new City(),UserRole.CLIENT,UserStatus.ACTIVE);
        userService.updateUser(1,updateUserDto);
    }

    @Test
    public void updateUserDniNull() throws UserNotFoundException {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(expected));
        UpdateUserDto updateUserDto = new UpdateUserDto("username","password","user","asd",null, new City(),UserRole.CLIENT,UserStatus.ACTIVE);
        userService.updateUser(1,updateUserDto);
    }

    @Test
    public void updateUserCityNull() throws UserNotFoundException {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(expected));
        UpdateUserDto updateUserDto = new UpdateUserDto("username","password","user","asd",2230202, null,UserRole.CLIENT,UserStatus.ACTIVE);
        userService.updateUser(1,updateUserDto);
    }

    @Test
    public void updateUserRoleNull() throws UserNotFoundException {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(expected));
        UpdateUserDto updateUserDto = new UpdateUserDto("username","password","user","asd",2230202, new City(),null,UserStatus.ACTIVE);
        userService.updateUser(1,updateUserDto);
    }

    @Test
    public void updateUserStatusNull() throws UserNotFoundException {
        User expected = new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(expected));
        UpdateUserDto updateUserDto = new UpdateUserDto("username","password","user","asd",2230202, new City(),UserRole.CLIENT,null);
        userService.updateUser(1,updateUserDto);
    }

    @Test(expected = UserNotFoundException.class)
    public void updateUserError() throws UserNotFoundException {
        Mockito.when(userRepository.findById(1).orElseThrow(UserNotFoundException::new)).thenThrow(new UserNotFoundException());
        userRepository.findById(1);
    }

    @Test
    public void existsByIdTrue() {
        Mockito.when(userRepository.existsById(1)).thenReturn(true);
        boolean returned = userService.existsById(1);
        assertEquals(true,returned);
    }


    @Test
    public void getJWTToken() {
    }

    @After
    public void tearDown() throws Exception {
    }
}
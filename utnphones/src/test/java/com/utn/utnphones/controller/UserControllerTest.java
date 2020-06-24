package com.utn.utnphones.controller;

import com.utn.utnphones.dto.UpdateUserDto;
import com.utn.utnphones.dto.UserLoginDto;
import com.utn.utnphones.exceptions.InvalidLoginException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.UserRole;
import com.utn.utnphones.model.enums.UserStatus;
import com.utn.utnphones.security.SessionManager;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.utn.utnphones.security.Constants.SECRET_KEY;
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
        assertEquals(HttpStatus.NO_CONTENT, returned.getStatusCode());
    }

    @Test
    public void deleteUser() throws UserNotFoundException {
        ResponseEntity returned = userController.deleteUser(1);
        assertNotNull(returned);
        assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test
    public void getUserByIdOk() throws UserNotFoundException {
        User returnedUser = new User(1, "siderjonas", "pastrana", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_CLIENT");

        String token = Jwts
                .builder()
                .setId("1")
                .setSubject("siderjonas")
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        when(userService.getUserById(1)).thenReturn(returnedUser);

        assertEquals(ResponseEntity.ok(returnedUser), userController.getUserById(1, token));

    }

    @Test
    public void getUserByIdError() throws UserNotFoundException {
        User returnedUser = new User(1, "siderjonas", "pastrana", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_CLIENT");

        String token = Jwts
                .builder()
                .setId("2")
                .setSubject("siderjonas")
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        assertEquals(ResponseEntity.status(HttpStatus.FORBIDDEN).build(), userController.getUserById(1, token));

    }

    @Test
    public void updateUser() throws UserNotFoundException {
        UpdateUserDto updateUserDto = new UpdateUserDto("siderjonas", "pastrana", "Christian", "Soldano", 40020327, null, UserRole.CLIENT, UserStatus.ACTIVE);

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_CLIENT");

        String token = Jwts
                .builder()
                .setId("1")
                .setSubject("siderjonas")
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        assertEquals(ResponseEntity.ok().build(), userController.updateUser(1, updateUserDto, token));
    }

    @Test
    public void updateUserError() throws UserNotFoundException {
        UpdateUserDto updateUserDto = new UpdateUserDto("siderjonas", "pastrana", "Christian", "Soldano", 40020327, null, UserRole.CLIENT, UserStatus.ACTIVE);

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_CLIENT");

        String token = Jwts
                .builder()
                .setId("2")
                .setSubject("siderjonas")
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        assertEquals(ResponseEntity.status(HttpStatus.FORBIDDEN).build(), userController.updateUser(1, updateUserDto, token));
    }

    @Test
    public void loginOk() throws InvalidLoginException, ValidationException, UserNotFoundException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String fakePassword = bCryptPasswordEncoder.encode("pastrana");
        User fakeUser = new User(1, "siderjonas", fakePassword, "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_CLIENT");

        String token = Jwts
                .builder()
                .setId("1")
                .setSubject("siderjonas")
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        when(userService.getUserByUsername("siderjonas")).thenReturn(fakeUser);
        when(userService.getJWTToken(1, "siderjonas", UserRole.CLIENT, sessionManager)).thenReturn(token);
        UserLoginDto userDto = new UserLoginDto(1, "siderjonas", token);

        assertEquals(ResponseEntity.ok(userDto), userController.login("siderjonas", "pastrana"));
    }

    @Test(expected = ValidationException.class)
    public void loginEmptyFields() throws InvalidLoginException, ValidationException, UserNotFoundException {
        userController.login(null, null);
    }

    @Test(expected = InvalidLoginException.class)
    public void loginInvalidPassword() throws InvalidLoginException, ValidationException, UserNotFoundException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String fakePassword = bCryptPasswordEncoder.encode("pastrana");
        User fakeUser = new User(1, "siderjonas", fakePassword, "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null);

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_CLIENT");

        String token = Jwts
                .builder()
                .setId("1")
                .setSubject("siderjonas")
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        when(userService.getUserByUsername("siderjonas")).thenReturn(fakeUser);

        userController.login("siderjonas", "asdasd");
    }

}
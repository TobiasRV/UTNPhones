package com.utn.utnphones.controller;

import com.utn.utnphones.dto.AddCallDto;
import com.utn.utnphones.dto.CallQueryReturnDto;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.model.Bill;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.service.CallService;
import com.utn.utnphones.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.utn.utnphones.security.Constants.SECRET_KEY;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallControllerTest {

    CallController callController;

    @Mock
    CallService callService;
    @Mock
    UserService userService;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        callController = new CallController(callService, userService);
    }

    @Test
    public void getAll() {
        Call c1 = new Call(1, null, null, null, null, 20, 20.0, 30.0, new Bill());
        Call c2 = new Call(2, null, null, null, null, 20, 20.0, 30.0, new Bill());


        List<Call> expected = new ArrayList<>();
        expected.add(c1);
        expected.add(c2);

        Mockito.when(callService.getAll()).thenReturn(expected);

        ResponseEntity<List<Call>> returned = callController.getAll();

        assertNotNull(returned);
        assertThat(returned.getBody().size(), is(2));
        assertEquals(returned.getBody(), expected);

    }

    @Test
    public void getAllEmpty() {
        List<Call> expected = new ArrayList<>();
        when(callService.getAll()).thenReturn(expected);
        ResponseEntity<List<Call>> returned = callController.getAll();
        assertNotNull(returned);
        assertEquals(HttpStatus.NO_CONTENT, returned.getStatusCode());
    }

    @Test
    public void addCall() {
        AddCallDto call = new AddCallDto();
        ResponseEntity returned = callController.addCall(call);
        assertNotNull(returned);
        assertEquals(HttpStatus.CREATED, returned.getStatusCode());
    }

    @Test
    public void getCallsByUser() throws UserNotFoundException {
        CallQueryReturnDto callQueryReturnDto = new CallQueryReturnDto(1, null, null, null, 1, 10, 15.0, 30.3, 1);
        CallQueryReturnDto callQueryReturnDto2 = new CallQueryReturnDto(2, null, null, null, 1, 10, 15.0, 30.3, 1);

        List<CallQueryReturnDto> callQueryReturnDtoList = new ArrayList<>();
        callQueryReturnDtoList.add(callQueryReturnDto);
        callQueryReturnDtoList.add(callQueryReturnDto2);

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
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        when(userService.existsById(1)).thenReturn(true);
        when(callService.getCallsByUserAndDate(1, Date.valueOf("2020-05-1"), Date.valueOf("2020-05-1"))).thenReturn(callQueryReturnDtoList);

        assertEquals(ResponseEntity.ok(callQueryReturnDtoList), callController.getCallsByUser(1, Date.valueOf("2020-05-1"), Date.valueOf("2020-05-1"), token));

    }

    @Test
    public void getCallsByUserForbidden() throws UserNotFoundException {
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
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        assertEquals(ResponseEntity.status(HttpStatus.FORBIDDEN).build(), callController.getCallsByUser(1, Date.valueOf("2020-05-1"), Date.valueOf("2020-05-1"), token));
    }

    @Test(expected = UserNotFoundException.class)
    public void getCallsByUserNotExist() throws UserNotFoundException {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_ADMIN");

        String token = Jwts
                .builder()
                .setId("1")
                .setSubject("siderjonas")
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        when(userService.existsById(1)).thenReturn(false);
        callController.getCallsByUser(1, Date.valueOf("2020-05-1"), Date.valueOf("2020-05-1"), token);
    }

    @Test
    public void getCallsByUserNullParameters() throws UserNotFoundException {
        CallQueryReturnDto callQueryReturnDto = new CallQueryReturnDto(1, null, null, null, 1, 10, 15.0, 30.3, 1);
        CallQueryReturnDto callQueryReturnDto2 = new CallQueryReturnDto(2, null, null, null, 1, 10, 15.0, 30.3, 1);

        List<CallQueryReturnDto> callQueryReturnDtoList = new ArrayList<>();
        callQueryReturnDtoList.add(callQueryReturnDto);
        callQueryReturnDtoList.add(callQueryReturnDto2);

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
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        when(userService.existsById(1)).thenReturn(true);
        when(callService.getCallsByUser(1)).thenReturn(callQueryReturnDtoList);

        assertEquals(ResponseEntity.ok(callQueryReturnDtoList), callController.getCallsByUser(1, null, null, token));

    }


    @Test
    public void getCallsByUserNoContent() throws UserNotFoundException {

        List<CallQueryReturnDto> callQueryReturnDtoList = new ArrayList<>();

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
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        when(userService.existsById(1)).thenReturn(true);
        when(callService.getCallsByUser(1)).thenReturn(callQueryReturnDtoList);

        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).build(), callController.getCallsByUser(1, null, null, token));

    }

    //    @Test
//    public void addCity() throws ProvinceNotFoundException {
//        PowerMockito.mockStatic(RestUtils.class);
//        Province p1 = new Province(1, null, null);
//        City expected = new City(1, "Miramar", p1, "2291");
//
//        when(provinceService.existsById(1)).thenReturn(true);
//        when(cityService.addCity(expected)).thenReturn(expected);
//
//        when(RestUtils.getCityLocation(expected)).thenReturn(URI.create("miUri.com"));
//
//        ResponseEntity<City> responseEntity = cityController.addCity(expected);
//
//        List<String> headers = responseEntity.getHeaders().get("location");
//        assertEquals(headers.get(0), "miUri.com");
//        assertEquals(expected, responseEntity.getBody());
//    }
}
package com.utn.utnphones.controller;

import com.utn.utnphones.dto.LineAndQtyOfCallsDto;
import com.utn.utnphones.dto.UpdateLineDto;
import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.exceptions.LineNotFoundException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.LineStatus;
import com.utn.utnphones.model.enums.LineType;
import com.utn.utnphones.model.enums.UserRole;
import com.utn.utnphones.model.enums.UserStatus;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.LineService;
import com.utn.utnphones.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.utn.utnphones.security.Constants.SECRET_KEY;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LineControllerTest {

    LineController lineController;

    @Mock
    LineService lineService;
    @Mock
    UserService userService;
    @Mock
    CityService cityService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        lineController = new LineController(lineService, userService, cityService);
    }


    @Test
    public void getAll() {
        Line l1 = new Line(1, null, null, "223-20202020", LineType.MOBILE, LineStatus.ACTIVE);
        Line l2 = new Line(1, null, null, "223-23232323", LineType.MOBILE, LineStatus.ACTIVE);

        List<Line> expected = new ArrayList<>();
        expected.add(l1);
        expected.add(l2);

        Mockito.when(lineService.getAll()).thenReturn(expected);

        assertEquals(ResponseEntity.ok(expected), lineController.getAll());
    }

    @Test
    public void getAllEmpty() {
        List<Line> lineList = new ArrayList<>();

        when(lineService.getAll()).thenReturn(lineList);

        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).build(), lineController.getAll());
    }

    @Test
    public void getTop10Destinations() throws UserNotFoundException {
        Line l1 = new Line(1, null, null, "223-20202020", LineType.MOBILE, LineStatus.ACTIVE);
        Line l2 = new Line(1, null, null, "223-23232323", LineType.MOBILE, LineStatus.ACTIVE);

        LineAndQtyOfCallsDto dto1 = new LineAndQtyOfCallsDto();
        dto1.setLine(l1);
        dto1.setQtyOfCalls(2);

        LineAndQtyOfCallsDto dto2 = new LineAndQtyOfCallsDto();
        dto1.setLine(l2);
        dto1.setQtyOfCalls(4);

        List<LineAndQtyOfCallsDto> expected = new ArrayList<>();
        expected.add(dto1);
        expected.add(dto2);

        Mockito.when(lineService.getTop10Destinations(1)).thenReturn(expected);
        Mockito.when(userService.existsById(1)).thenReturn(true);

        ResponseEntity<List<LineAndQtyOfCallsDto>> returned = lineController.getTop10Destinations(1);

        assertNotNull(returned);
        assertThat(returned.getBody().size(), is(2));
        assertEquals(expected, returned.getBody());
    }

    @Test
    public void getTop10DestinationsEmpty() throws UserNotFoundException {
        List<LineAndQtyOfCallsDto> expected = new ArrayList<>();

        Mockito.when(lineService.getTop10Destinations(1)).thenReturn(expected);
        Mockito.when(userService.existsById(1)).thenReturn(true);

        ResponseEntity<List<LineAndQtyOfCallsDto>> returned = lineController.getTop10Destinations(1);

        assertNotNull(returned);
        assertEquals(HttpStatus.NO_CONTENT, returned.getStatusCode());
    }

    @Test(expected = UserNotFoundException.class)
    public void getTop10DestinationsError() throws UserNotFoundException {
        Mockito.when(userService.existsById(1)).thenReturn(false);
        lineController.getTop10Destinations(1);
    }


    @Test
    public void getLineByUserId() throws UserNotFoundException {
        Line l1 = new Line(1, null, null, "223-20202020", LineType.MOBILE, LineStatus.ACTIVE);
        Line l2 = new Line(1, null, null, "223-23232323", LineType.MOBILE, LineStatus.ACTIVE);

        List<Line> lineList = new ArrayList<>();
        lineList.add(l1);
        lineList.add(l2);

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

        when(userService.existsById(1)).thenReturn(true);
        when(lineService.getLinesByUserId(1)).thenReturn(lineList);

        assertEquals(ResponseEntity.ok(lineList), lineController.getLinesByUserId(1, token));
    }

    @Test(expected = UserNotFoundException.class)
    public void getLineByUserIdUserNotFound() throws UserNotFoundException {

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

        when(userService.existsById(1)).thenReturn(false);
        lineController.getLinesByUserId(1, token);
    }

    @Test
    public void getLineByUserIdForbidden() throws UserNotFoundException {

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

        when(userService.existsById(1)).thenReturn(true);
        assertEquals(ResponseEntity.status(HttpStatus.FORBIDDEN).build(), lineController.getLinesByUserId(1, token));
    }

    @Test
    public void getLineByUserIdNoContent() throws UserNotFoundException {
        List<Line> lineList = new ArrayList<>();

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
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        when(userService.existsById(1)).thenReturn(true);
        when(lineService.getLinesByUserId(1)).thenReturn(lineList);

        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).build(), lineController.getLinesByUserId(1, token));
    }


    @Test
    public void getLineById() throws LineNotFoundException {
        User u = new User(1,"username","password","asdasd@asdasd.com","name","lastname",40020327,null,"callefalsa123", UserRole.CLIENT, UserStatus.ACTIVE,null);
        Line l1 = new Line(1, u, null, "223-20202020", LineType.MOBILE, LineStatus.ACTIVE);

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
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        when(lineService.getLineById(1)).thenReturn(l1);

        assertEquals(ResponseEntity.ok(l1),lineController.getLineById(1,token));
    }

    @Test
    public void getLineByIdForbidden() throws LineNotFoundException {
        User u = new User(1,"username","password","asdasd@asdasd.com","name","lastname",40020327,null,"callefalsa123", UserRole.CLIENT, UserStatus.ACTIVE,null);

        Line l1 = new Line(1, u, null, "223-20202020", LineType.MOBILE, LineStatus.ACTIVE);

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

        when(lineService.getLineById(1)).thenReturn(l1);

        assertEquals(ResponseEntity.status(HttpStatus.FORBIDDEN).build(),lineController.getLineById(1,token));
    }

    @Test
    public void updateLine() throws UserNotFoundException, CityNotFoundException, LineNotFoundException {
        UpdateLineDto updateLineDto = new UpdateLineDto(1, "223232", LineType.MOBILE, LineStatus.ACTIVE);

        Mockito.when(lineService.existsById(1)).thenReturn(true);


        ResponseEntity result = lineController.updateLine(1, updateLineDto);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());


    }

    @Test
    public void updateLineNullCity() throws UserNotFoundException, CityNotFoundException, LineNotFoundException {
        UpdateLineDto updateLineDto = new UpdateLineDto(null, "223232", LineType.MOBILE, LineStatus.ACTIVE);

        Mockito.when(lineService.existsById(1)).thenReturn(true);


        ResponseEntity result = lineController.updateLine(1, updateLineDto);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test(expected = LineNotFoundException.class)
    public void updateLineError() throws UserNotFoundException, CityNotFoundException, LineNotFoundException {
        UpdateLineDto updateLineDto = new UpdateLineDto(null, "223232", LineType.MOBILE, LineStatus.ACTIVE);
        Mockito.when(lineService.existsById(1)).thenReturn(false);
        lineController.updateLine(1, updateLineDto);
    }

    @Test
    public void deleteLine() throws LineNotFoundException {
        ResponseEntity returned = lineController.deleteLine(1);
        assertNotNull(returned);
        assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

}
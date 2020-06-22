package com.utn.utnphones.controller;

import com.utn.utnphones.dto.AddCallDto;
import com.utn.utnphones.dto.CallQueryReturnDto;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.model.Bill;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.BillStatus;
import com.utn.utnphones.service.CallService;
import com.utn.utnphones.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallControllerTest {

    CallController controller;

    @Mock
    CallService callService;
    @Mock
    UserService userService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new CallController(callService, userService);
    }

    @Test
    public void getAll() {
        Call c1 = new Call(1, null, null, null, null, 20, 20.0, 30.0, new Bill());
        Call c2 = new Call(2, null, null, null, null, 20, 20.0, 30.0, new Bill());


        List<Call> expected = new ArrayList<>();
        expected.add(c1);
        expected.add(c2);

        Mockito.when(callService.getAll()).thenReturn(expected);

        ResponseEntity<List<Call>> returned = controller.getAll();

        assertNotNull(returned);
        assertThat(returned.getBody().size(), is(2));
        assertEquals(returned.getBody(), expected);

    }

    @Test
    public void getAllEmpty() {
        HttpStatus response = null;
        List<Call> expected = new ArrayList<>();

        when(callService.getAll()).thenReturn(expected);

        List<Call> returned = callService.getAll();

        if(returned.size() == 0) {
            response = HttpStatus.NO_CONTENT;
        }
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response);
    }


    @Test
    public void getCallsByUser() {
        CallQueryReturnDto c1 = new CallQueryReturnDto(1, null, null, null, null, 20, 20.0, 30.0, 1);
        CallQueryReturnDto c2 = new CallQueryReturnDto(2, null, null, null, null, 20, 20.0, 30.0, 2);

        List<CallQueryReturnDto> expected = new ArrayList<>();
        expected.add(c1);
        expected.add(c2);

        Mockito.when(callService.getCallsByUser(1)).thenReturn(expected);

        List<CallQueryReturnDto> returned = callService.getCallsByUser(1);

        assertNotNull(returned);
        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);

    }

    @Test
    public void getCallsByUserEmpty() {
        HttpStatus response = null;
        List<CallQueryReturnDto> expected = new ArrayList<>();

        when(callService.getCallsByUser(1)).thenReturn(expected);

        List<CallQueryReturnDto> returned = callService.getCallsByUser(1);

        if (returned.size() == 0) {
            response = HttpStatus.NO_CONTENT;
        }
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response);
    }

    @Test
    public void getCallsByUserAndDate() throws UserNotFoundException {
        CallQueryReturnDto c1 = new CallQueryReturnDto(1, null, null, null, null, 20, 20.0, 30.0, 1);
        CallQueryReturnDto c2 = new CallQueryReturnDto(2, null, null, null, null, 20, 20.0, 30.0, 2);


        List<CallQueryReturnDto> expected = new ArrayList<>();
        expected.add(c1);
        expected.add(c2);


        Mockito.when(callService.getCallsByUserAndDate(1, Date.valueOf("2020-05-1"), Date.valueOf("2020-06-1"))).thenReturn(expected);

        List<CallQueryReturnDto> returned = callService.getCallsByUserAndDate(1, Date.valueOf("2020-05-1"), Date.valueOf("2020-06-1"));

        assertNotNull(returned);
        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);

    }

    @Test
    public void getCallsByUserAndDateEmpty() {
        HttpStatus response = null;
        List<CallQueryReturnDto> expected = new ArrayList<>();

        when(callService.getCallsByUserAndDate(1, null, null)).thenReturn(expected);

        List<CallQueryReturnDto> returned = callService.getCallsByUserAndDate(1, null, null);

        if (returned.size() == 0) {
            response = HttpStatus.NO_CONTENT;
        }
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response);
    }

    @After
    public void tearDown() throws Exception {
    }

}
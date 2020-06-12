package com.utn.utnphones.controller;

import com.utn.utnphones.dto.AddCallDto;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.model.Bill;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.model.enums.BillStatus;
import com.utn.utnphones.service.CallService;
import com.utn.utnphones.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
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
        controller = new CallController(callService,userService);
    }

    @Test
    public void getAll() {
        Call c1 = new Call(1,null,null,null,null,20,20.0,30.0,false);
        Call c2 = new Call(2,null,null,null,null,20,20.0,30.0,false);


        List<Call> expected = new ArrayList<>();
        expected.add(c1);
        expected.add(c2);

        Mockito.when(callService.getAll()).thenReturn(expected);

        ResponseEntity<List<Call>> returned = controller.getAll();


        if (returned != null) {
            assertThat(returned.getBody().size(), is(2));
        }

        assertEquals(returned.getBody(), expected);

    }

    @Test
    public void addCall() {
        AddCallDto c = new AddCallDto("223-23232323","2291-123123123",null,30);

    }

    @Test
    public void getCallsByUser() {
        Call c1 = new Call(1,null,null,null,null,20,20.0,30.0,false);
        Call c2 = new Call(2,null,null,null,null,20,20.0,30.0,false);


        List<Call> expected = new ArrayList<>();
        expected.add(c1);
        expected.add(c2);

        Mockito.when(callService.getCallsByUser(1)).thenReturn(expected);

        List<Call> returned = callService.getCallsByUser(1);


        if (returned != null) {
            assertThat(returned.size(), is(2));
        }

        //check if userlist contains the same elements
        assertEquals(returned, expected);

    }

    @Test
    public void getCallsByUserAndDate() throws UserNotFoundException {
        Call c1 = new Call(1,null,null, Timestamp.valueOf("2020-05-2 13:00:00"),null,20,20.0,30.0,false);
        Call c2 = new Call(2,null,null,Timestamp.valueOf("2020-05-4 13:00:00"),null,20,20.0,30.0,false);


        List<Call> expected = new ArrayList<>();
        expected.add(c1);
        expected.add(c2);


        Mockito.when(callService.getCallsByUserAndDate(1, Date.valueOf("2020-05-1"),Date.valueOf("2020-06-1"))).thenReturn(expected);

        List<Call> returned = callService.getCallsByUserAndDate(1,Date.valueOf("2020-05-1"),Date.valueOf("2020-06-1"));


        if (returned != null) {
            assertThat(returned.size(),is(2));
        }

        assertEquals(returned, expected);

    }
}
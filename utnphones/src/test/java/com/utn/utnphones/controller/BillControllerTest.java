package com.utn.utnphones.controller;

import com.utn.utnphones.exceptions.BillNotFoundException;
import com.utn.utnphones.exceptions.LineNotFoundException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.Bill;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.BillStatus;
import com.utn.utnphones.service.BillService;
import com.utn.utnphones.service.LineService;
import com.utn.utnphones.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.ResponseEntity.*;

public class BillControllerTest {

    BillController controller;

    @Mock
    BillService billService;
    @Mock
    UserService userService;
    @Mock
    LineService lineService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new BillController(billService, userService, lineService);
    }

    @Test
    public void getAll() {
        Bill b1 = new Bill(1, null, null, 5, 1.00, 2.00, null, null, BillStatus.UNPAID);
        Bill b2 = new Bill(2, null, null, 5, 1.00, 2.00, null, null, BillStatus.UNPAID);

        List<Bill> expected = new ArrayList<>();
        expected.add(b1);
        expected.add(b2);

        Mockito.when(billService.getAll()).thenReturn(expected);

        List<Bill> returned = billService.getAll();

        assertNotNull(returned);
        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);

    }

    @Test
    public void getAllEmpty() {
        HttpStatus response = null;
        List<Bill> expected = new ArrayList<>();

        when(billService.getAll()).thenReturn(expected);

        List<Bill> returned = billService.getAll();

        if (returned.size() == 0) {
            response = HttpStatus.NO_CONTENT;
        }

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response);
    }

    @Test
    public void getBillsByUser() {
        Bill b1 = new Bill(1, null, null, 5, 1.00, 2.00, null, null, BillStatus.UNPAID);
        Bill b2 = new Bill(2, null, null, 5, 1.00, 2.00, null, null, BillStatus.UNPAID);


        List<Bill> expected = new ArrayList<>();
        expected.add(b1);
        expected.add(b2);

        Mockito.when(billService.getBillsByUser(1)).thenReturn(expected);

        List<Bill> returned = billService.getBillsByUser(1);

        assertNotNull(returned);
        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);
    }

    @Test
    public void getBillByUserEmpty() {

        HttpStatus response = null;

        List<Bill> expected = new ArrayList<>();

        Mockito.when(billService.getBillsByUser(2)).thenReturn(expected);

        List<Bill> returned = billService.getBillsByUser(2);

        if (returned.size() == 0) {
            response = HttpStatus.NO_CONTENT;
        }
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response);

    }


    @Test
    public void getBillByDate() {
        Bill b1 = new Bill(1, null, null, 5, 1.00, 2.00, null, null, BillStatus.UNPAID);
        Bill b2 = new Bill(2, null, null, 5, 1.00, 2.00, null, null, BillStatus.UNPAID);


        List<Bill> expected = new ArrayList<>();
        expected.add(b1);
        expected.add(b2);

        Mockito.when(billService.getBillsByUserAndDate(1, null, null)).thenReturn(expected);

        List<Bill> returned = billService.getBillsByUserAndDate(1, null, null);

        assertNotNull(returned);
        assertThat(returned.size(), is(2));
        assertEquals(expected, returned);
    }


    @Test
    public void getBillByDateEmpty() {

        HttpStatus response = null;

        List<Bill> expected = new ArrayList<>();

        Mockito.when(billService.getBillsByUserAndDate(2, null, null)).thenReturn(expected);

        List<Bill> returned = billService.getBillsByUserAndDate(2, null, null);

        if (returned.size() == 0) {
            response = HttpStatus.NO_CONTENT;
        }
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response);

    }


    @Test
    public void getBillById() throws ValidationException, BillNotFoundException {
        Bill expected = new Bill(1, null, null, 5, 1.00, 2.00, null, null, BillStatus.UNPAID);


        Mockito.when(billService.getBillById(1, 1)).thenReturn(expected);

        Bill returned = billService.getBillById(1, 1);

        assertNotNull(returned);
        assertEquals(returned, expected);
    }

    @After
    public void tearDown() throws Exception {
    }

}
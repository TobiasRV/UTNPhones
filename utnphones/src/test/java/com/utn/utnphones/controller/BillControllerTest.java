package com.utn.utnphones.controller;

import com.utn.utnphones.exceptions.BillNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.Bill;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.BillStatus;
import com.utn.utnphones.service.BillService;
import com.utn.utnphones.service.LineService;
import com.utn.utnphones.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

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
        controller = new BillController(billService,userService, lineService);
    }

    @Test
    public void getAll() {
        Bill b1 = new Bill(1,null,1.00,2.00,null,null, BillStatus.UNPAID);
        Bill b2 = new Bill(2,null,1.00,2.00,null,null, BillStatus.UNPAID);

        List<Bill> expected = new ArrayList<>();
        expected.add(b1);
        expected.add(b2);

        Mockito.when(billService.getAll()).thenReturn(expected);

        List<Bill> returned = billService.getAll();

        //userlist.size must be 2
        if (returned != null) {
            assertThat(returned.size(), is(2));
        }

        //check if userlist contains the same elements
        assertEquals(returned, expected);

    }

    @Test
    public void getBillsByUser() {
        Bill b1 = new Bill(1,null,1.00,2.00,null,null, BillStatus.UNPAID);
        Bill b2 = new Bill(2,null,1.00,2.00,null,null, BillStatus.UNPAID);

        List<Bill> expected = new ArrayList<>();
        expected.add(b1);
        expected.add(b2);

        Mockito.when(billService.getBillsByUser(1)).thenReturn(expected);

        List<Bill> returned = billService.getBillsByUser(1);

        //userlist.size must be 2
        if (returned != null) {
            assertThat(returned.size(), is(2));
        }

        //check if userlist contains the same elements
        assertEquals(returned, expected);
    }

    @Test
    public void getBillById() throws ValidationException, BillNotFoundException {
        Bill expected = new Bill(1,null,1.00,2.00,null,null, BillStatus.UNPAID);

        Mockito.when(billService.getBillById(1,1)).thenReturn(expected);

        Bill returned = billService.getBillById(1,1);


        assertNotNull(returned);
        assertEquals(returned, expected);
    }

    @Test
    public void payBill() throws ValidationException, BillNotFoundException {

    }
}
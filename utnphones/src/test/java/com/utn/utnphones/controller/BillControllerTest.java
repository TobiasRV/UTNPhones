package com.utn.utnphones.controller;

import com.utn.utnphones.exceptions.BillNotFoundException;
import com.utn.utnphones.exceptions.LineNotFoundException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.Bill;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.BillStatus;
import com.utn.utnphones.model.enums.UserRole;
import com.utn.utnphones.model.enums.UserStatus;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isNull;
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

        ResponseEntity<List<Bill>> returned = controller.getAll();

        assertNotNull(returned);
        assertThat(returned.getBody().size(), is(2));
        assertEquals(returned.getBody(), expected);

    }



    @Test
    public void getAllEmpty() {
        List<Bill> expected = new ArrayList<>();
        when(billService.getAll()).thenReturn(expected);
        ResponseEntity<List<Bill>> returned = controller.getAll();

        assertNotNull(returned);
        assertEquals(HttpStatus.NO_CONTENT, returned.getStatusCode());
    }

    @Test
    public void getBillsByUser() throws UserNotFoundException {
        Bill b1 = new Bill(1, null, new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null), 5, 1.00, 2.00, null, null, BillStatus.UNPAID);
        Bill b2 = new Bill(2, null, new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null), 5, 1.00, 2.00, null, null, BillStatus.UNPAID);


        List<Bill> expected = new ArrayList<>();
        expected.add(b1);
        expected.add(b2);

        Mockito.when(billService.getBillsByUser(1)).thenReturn(expected);
        Mockito.when(userService.existsById(1)).thenReturn(true);
        ResponseEntity<List<Bill>> returned = controller.getBillsByUser(1,null,null);

        assertNotNull(returned);
        assertThat(returned.getBody().size(), is(2));
        assertEquals(expected, returned.getBody());
    }


    @Test
    public void getBillByUserEmpty() throws UserNotFoundException {
        List<Bill> expected = new ArrayList<>();

        Mockito.when(billService.getBillsByUser(2)).thenReturn(expected);
        Mockito.when(userService.existsById(2)).thenReturn(true);

        ResponseEntity<List<Bill>> returned = controller.getBillsByUser(2,null,null);

        assertNotNull(returned);
        assertEquals(HttpStatus.NO_CONTENT, returned.getStatusCode());

    }

    @Test(expected = UserNotFoundException.class)
    public void getBillByUserNotExist() throws UserNotFoundException {
        Mockito.when(userService.existsById(1)).thenReturn(false);
        controller.getBillsByUser(1,null,null);
    }

    @Test
    public void getBillByDate() throws UserNotFoundException {
        Bill b1 = new Bill(1, null, new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null), 5, 1.00, 2.00, null, null, BillStatus.UNPAID);
        Bill b2 = new Bill(2, null, new User(1, "user1", "pass", "soldanochristian@hotmail.com", "name1", "lastname1", 40020327, null, "Manuel Acevedo 2685", UserRole.CLIENT, UserStatus.ACTIVE, null), 5, 1.00, 2.00, null, null, BillStatus.UNPAID);


        List<Bill> expected = new ArrayList<>();
        expected.add(b1);
        expected.add(b2);

        Mockito.when(billService.getBillsByUserAndDate(1, new Date(2020,2,4),new Date(2020,3,20))).thenReturn(expected);
        Mockito.when(userService.existsById(1)).thenReturn(true);
        ResponseEntity<List<Bill>> returned = controller.getBillsByUser(1, new Date(2020,2,4),new Date(2020,3,20));

        assertNotNull(returned);
        assertThat(returned.getBody().size(), is(2));
        assertEquals(expected, returned.getBody());
    }


    @Test
    public void getBillByDateEmpty() throws UserNotFoundException {

        List<Bill> expected = new ArrayList<>();

        Mockito.when(billService.getBillsByUserAndDate(2, new Date(2020,2,4),new Date(2020,3,20))).thenReturn(expected);
        Mockito.when(userService.existsById(2)).thenReturn(true);
        ResponseEntity<List<Bill>> returned = controller.getBillsByUser(2,new Date(2020,2,4),new Date(2020,3,20));

        assertNotNull(returned);
        assertEquals(HttpStatus.NO_CONTENT, returned.getStatusCode());

    }


    @Test
    public void getBillById() throws ValidationException, BillNotFoundException, LineNotFoundException {
        Bill expected = new Bill(1, null, null, 5, 1.00, 2.00, null, null, BillStatus.UNPAID);

        Mockito.when(billService.getBillById(1, 1)).thenReturn(expected);
        Mockito.when(lineService.existsById(1)).thenReturn(true);
        ResponseEntity<Bill> returned = controller.getBillById(1, 1);

        assertNotNull(returned);
        assertEquals(expected,returned.getBody());
    }

    @Test(expected = LineNotFoundException.class)
    public void getBillByIdError() throws LineNotFoundException, ValidationException, BillNotFoundException {
        Mockito.when(lineService.existsById(1)).thenReturn(false);
        controller.getBillById(1,1);
    }

    @Test
    public void payBill() throws BillNotFoundException, ValidationException, LineNotFoundException {
        Mockito.when(lineService.existsById(1)).thenReturn(true);
        ResponseEntity returned = controller.payBill(1,1);
        assertNotNull(returned);
        assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test(expected = LineNotFoundException.class)
    public void payBillError() throws LineNotFoundException, ValidationException, BillNotFoundException {
        Mockito.when(lineService.existsById(1)).thenReturn(false);
        controller.payBill(1,1);
    }


    @After
    public void tearDown() throws Exception {
    }

}
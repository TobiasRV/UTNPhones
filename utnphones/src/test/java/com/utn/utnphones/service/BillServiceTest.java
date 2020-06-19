package com.utn.utnphones.service;

import com.utn.utnphones.exceptions.BillNotFoundException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.Bill;
import com.utn.utnphones.model.City;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.BillStatus;
import com.utn.utnphones.model.enums.LineStatus;
import com.utn.utnphones.model.enums.LineType;
import com.utn.utnphones.repository.BillRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class BillServiceTest {

    BillService billService;

    @Mock
    BillRepository billRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        billService = new BillService(billRepository);
    }

    @Test
    public void getAll() {
        Bill b1 = new Bill(1, null, null, 5, 1.00, 2.00, null, null, BillStatus.UNPAID);
        Bill b2 = new Bill(2, null, null, 5, 1.00, 2.00, null, null, BillStatus.UNPAID);

        List<Bill> expected = new ArrayList<>();
        expected.add(b1);
        expected.add(b2);

        Mockito.when(billRepository.findAll()).thenReturn(expected);

        List<Bill> returned = billRepository.findAll();

        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);
    }

    @Test
    public void getBillsByUserAndDate() {
        Bill b1 = new Bill(1, null, null, 5, 1.00, 2.00, new Timestamp(System.currentTimeMillis()), null, BillStatus.UNPAID);
        Bill b2 = new Bill(2, null, null, 5, 1.00, 2.00, new Timestamp(System.currentTimeMillis()), null, BillStatus.UNPAID);

        List<Bill> expected = new ArrayList<>();
        expected.add(b1);
        expected.add(b2);

        Mockito.when(billRepository.getBillsByUserAndDate(1, null, null)).thenReturn(expected);

        List<Bill> returned = billRepository.getBillsByUserAndDate(1, null, null);

        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);
    }

    @Test
    public void getBillsByUser() {
        Bill b1 = new Bill(1, null, null, 5, 1.00, 2.00, null, null, BillStatus.UNPAID);
        Bill b2 = new Bill(2, null, null, 5, 1.00, 2.00, null, null, BillStatus.UNPAID);

        List<Bill> expected = new ArrayList<>();
        expected.add(b1);
        expected.add(b2);

        Mockito.when(billRepository.getBillsByUser(1)).thenReturn(expected);

        List<Bill> returned = billRepository.getBillsByUser(1);

        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);
    }


    @Test
    public void getBillById() {
        Bill expected = new Bill(1, null, null, 5, 1.00, 2.00, null, null, BillStatus.UNPAID);

        Mockito.when(billRepository.findById(1)).thenReturn(Optional.of(expected));

        Optional<Bill> returned = billRepository.findById(1);


        assertEquals(expected,returned.get());
    }

    @Test(expected = BillNotFoundException.class)
    public void getBillByIdNotFoundError() throws BillNotFoundException {

        Mockito.when(billRepository.findById(1).orElseThrow(BillNotFoundException::new)).thenThrow(new BillNotFoundException());
        billRepository.findById(1);
    }


    @Test(expected = BillNotFoundException.class)
    public void payBillNotFoundError() throws BillNotFoundException {

        Mockito.when(billRepository.findById(1).orElseThrow(BillNotFoundException::new)).thenThrow(new BillNotFoundException());
        billRepository.findById(1);
    }


    /*
    @Test
    public void payBill() {
        Bill b1 = new Bill(1, new Line(1,new User(),new City(),"202020202", LineType.MOBILE, LineStatus.ACTIVE), null, 5, 1.00, 2.00, null, null, BillStatus.UNPAID);

        ValidationException returned;
        if (!b1.getLine().getIdLine().equals(1)){
            returned = new ValidationException("The bill id given does not match with the line id");
        }

        assertSame();
    }

     */
}
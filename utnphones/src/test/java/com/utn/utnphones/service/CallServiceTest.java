package com.utn.utnphones.service;

import com.utn.utnphones.model.Call;
import com.utn.utnphones.repository.CallRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallServiceTest {

    CallService callService;

    @Mock
    CallRepository callRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        callService = new CallService(callRepository);
    }

    @Test
    public void getAll() {
        Call c1 = new Call(1,null,null,null,null,null,null,null,null);
        Call c2 = new Call(2,null,null,null,null,null,null,null,null);

        List<Call> expected = new ArrayList<Call>();
        expected.add(c1);
        expected.add(c2);

        Mockito.when(callRepository.findAll()).thenReturn(expected);

        List<Call> returned = callRepository.findAll();
        assertThat(returned.size(), is(2));
        assertEquals(expected,returned);
    }

    @Test
    public void addCall() {
    }

    @Test
    public void getCallsByUserAndDate() {
        Call c1 = new Call(1,null,null,null,null,null,null,null,null);
        Call c2 = new Call(2,null,null,null,null,null,null,null,null);

        List<Call> expected = new ArrayList<Call>();
        expected.add(c1);
        expected.add(c2);
        Mockito.when(callRepository.getCallsByUserAndDate(1, null, null)).thenReturn(expected);

        List<Call> returned = callRepository.getCallsByUserAndDate(1, null, null);
        assertThat(returned.size(), is(2));
        assertEquals(expected,returned);

    }

    @Test
    public void getCallsByUser() {
        Call c1 = new Call(1,null,null,null,null,null,null,null,null);
        Call c2 = new Call(2,null,null,null,null,null,null,null,null);

        List<Call> expected = new ArrayList<Call>();
        expected.add(c1);
        expected.add(c2);
        Mockito.when(callRepository.getCallsByUser(1)).thenReturn(expected);

        List<Call> returned = callRepository.getCallsByUser(1);
        assertThat(returned.size(), is(2));
        assertEquals(expected,returned);
    }
}

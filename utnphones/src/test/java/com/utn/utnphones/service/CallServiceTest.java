package com.utn.utnphones.service;

import com.utn.utnphones.dto.AddCallDto;
import com.utn.utnphones.dto.CallQueryReturnDto;
import com.utn.utnphones.model.*;
import com.utn.utnphones.model.enums.LineStatus;
import com.utn.utnphones.model.enums.LineType;
import com.utn.utnphones.repository.CallRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Timestamp;
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
    public void addCall(){
        AddCallDto addCallDto = new AddCallDto("22232223","123123123",new Timestamp(System.currentTimeMillis()),20);
        callService.addCall(addCallDto);
    }

    @Test
    public void getAll() {
        Call c1 = new Call(1,null,null,null,null,null,null,null,null);
        Call c2 = new Call(2,null,null,null,null,null,null,null,null);

        List<Call> expected = new ArrayList<Call>();
        expected.add(c1);
        expected.add(c2);

        Mockito.when(callRepository.findAll()).thenReturn(expected);

        List<Call> returned = callService.getAll();
        assertThat(returned.size(), is(2));
        assertEquals(expected,returned);
    }



    @Test
    public void getCallsByUserAndDate() {
        Call c1 = new Call(1,new Line(1,new User(),new City(),"11111111", LineType.MOBILE, LineStatus.ACTIVE),new Line(2,new User(),new City(),"2222222", LineType.MOBILE, LineStatus.ACTIVE),new Timestamp(System.currentTimeMillis()),new Rate(),222,22.00,33.00,new Bill());
        Call c2 = new Call(2,new Line(1,new User(),new City(),"11111111", LineType.MOBILE, LineStatus.ACTIVE),new Line(2,new User(),new City(),"2222222", LineType.MOBILE, LineStatus.ACTIVE),new Timestamp(System.currentTimeMillis()),new Rate(),222,22.00,33.00,new Bill());

        List<Call> callList = new ArrayList<Call>();
        callList.add(c1);
        callList.add(c2);

        List<CallQueryReturnDto> expected = new ArrayList<>();
        for (Call c : callList) {
            expected.add(
                    CallQueryReturnDto.builder().idCall(c.getIdCall()).originLine(c.getOriginLine())
                            .destinationLine(c.getDestinationLine()).callDate(c.getCallDate())
                            .idRate(c.getRate().getIdRate()).callDuration(c.getCallDuration())
                            .callCost(c.getCallCost()).callPrice(c.getCallPrice())
                            .idBill(c.getBill().getIdBill()).build());
        }

        Mockito.when(callRepository.getCallsByUserAndDate(1,null, null)).thenReturn(callList);

        List<CallQueryReturnDto> returned = callService.getCallsByUserAndDate(1, null, null);

        assertNotNull(returned);
        assertEquals(expected,returned);

    }


    @Test
    public void getCallsByUser() {
        Call c1 = new Call(1,new Line(1,new User(),new City(),"11111111", LineType.MOBILE, LineStatus.ACTIVE),new Line(2,new User(),new City(),"2222222", LineType.MOBILE, LineStatus.ACTIVE),new Timestamp(System.currentTimeMillis()),new Rate(),222,22.00,33.00,new Bill());
        Call c2 = new Call(2,new Line(1,new User(),new City(),"11111111", LineType.MOBILE, LineStatus.ACTIVE),new Line(2,new User(),new City(),"2222222", LineType.MOBILE, LineStatus.ACTIVE),new Timestamp(System.currentTimeMillis()),new Rate(),222,22.00,33.00,new Bill());

        List<Call> callList = new ArrayList<Call>();
        callList.add(c1);
        callList.add(c2);

        List<CallQueryReturnDto> expected = new ArrayList<>();
        for (Call c : callList) {
            expected.add(
                    CallQueryReturnDto.builder().idCall(c.getIdCall()).originLine(c.getOriginLine())
                            .destinationLine(c.getDestinationLine()).callDate(c.getCallDate())
                            .idRate(c.getRate().getIdRate()).callDuration(c.getCallDuration())
                            .callCost(c.getCallCost()).callPrice(c.getCallPrice())
                            .idBill(c.getBill().getIdBill()).build());
        }

        Mockito.when(callRepository.getCallsByUser(1)).thenReturn(callList);

        List<CallQueryReturnDto> returned = callService.getCallsByUser(1);

        assertNotNull(returned);
        assertEquals(expected, returned);
    }

    @After
    public void tearDown() throws Exception {
    }
}

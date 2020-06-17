package com.utn.utnphones.controller;

import com.utn.utnphones.dto.LineAndQtyOfCallsDto;
import com.utn.utnphones.model.Bill;
import com.utn.utnphones.model.City;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.enums.LineStatus;
import com.utn.utnphones.model.enums.LineType;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.LineService;
import com.utn.utnphones.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LineControllerTest {

    LineController controller;

    @Mock
    LineService lineService;
    @Mock
    UserService userService;
    @Mock
    CityService cityService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new LineController(lineService, userService, cityService);
    }

    @Test
    public void getAll() {
        Line l1 = new Line(1, null, null, "223-20202020", LineType.MOBILE, LineStatus.ACTIVE);
        Line l2 = new Line(1, null, null, "223-23232323", LineType.MOBILE, LineStatus.ACTIVE);

        List<Line> expected = new ArrayList<>();
        expected.add(l1);
        expected.add(l2);

        Mockito.when(lineService.getAll()).thenReturn(expected);

        List<Line> returned = lineService.getAll();

        if (returned != null) {
            assertThat(returned.size(), is(2));
        }

        assertEquals(returned, expected);
    }

    @Test
    public void getAllEmpty() {
        List<Line> expected = new ArrayList<>();

        when(lineService.getAll()).thenReturn(expected);

        List<Line> returned = lineService.getAll();

        assertThat(returned.size(), is(0));
    }

    // TODO Hacer test
    @Test
    public void addLine() {
    }

    @Test
    public void updateLine() {

    }

    @Test
    public void getTop10Destinations() {
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

        List<LineAndQtyOfCallsDto> returned = lineService.getTop10Destinations(1);

        if (returned != null) {
            assertThat(returned.size(), is(2));
        }

        assertEquals(returned, expected);
    }
}
package com.utn.utnphones.controller;

import com.utn.utnphones.dto.CallQueryReturnDto;
import com.utn.utnphones.dto.LineAndQtyOfCallsDto;
import com.utn.utnphones.dto.UpdateLineDto;
import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.exceptions.LineNotFoundException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.model.Bill;
import com.utn.utnphones.model.City;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.enums.LineStatus;
import com.utn.utnphones.model.enums.LineType;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.LineService;
import com.utn.utnphones.service.UserService;
import org.hibernate.sql.Update;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
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

        assertNotNull(returned);
        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);
    }

    @Test
    public void getAllEmpty() {
        HttpStatus response = null;
        List<Line> expected = new ArrayList<>();

        when(lineService.getAll()).thenReturn(expected);

        List<Line> returned = lineService.getAll();

        if (returned.size() == 0) {
            response = HttpStatus.NO_CONTENT;
        }
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response);
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

        ResponseEntity<List<LineAndQtyOfCallsDto>> returned = controller.getTop10Destinations(1);

        assertNotNull(returned);
        assertThat(returned.getBody().size(), is(2));
        assertEquals(expected, returned.getBody());
    }

    @Test
    public void getTop10DestinationsEmpty() throws UserNotFoundException {
        List<LineAndQtyOfCallsDto> expected = new ArrayList<>();

        Mockito.when(lineService.getTop10Destinations(1)).thenReturn(expected);
        Mockito.when(userService.existsById(1)).thenReturn(true);

        ResponseEntity<List<LineAndQtyOfCallsDto>> returned = controller.getTop10Destinations(1);

        assertNotNull(returned);
        assertEquals(HttpStatus.NO_CONTENT, returned.getStatusCode());
    }

    @Test(expected = UserNotFoundException.class)
    public void getTop10DestinationsError() throws UserNotFoundException {
        Mockito.when(userService.existsById(1)).thenReturn(false);
        controller.getTop10Destinations(1);
    }


    @Test
    public void getLinesByUserId() {
        Line l1 = new Line(1, null, null, "223-20202020", LineType.MOBILE, LineStatus.ACTIVE);
        Line l2 = new Line(1, null, null, "223-23232323", LineType.MOBILE, LineStatus.ACTIVE);

        List<Line> expected = new ArrayList<>();
        expected.add(l1);
        expected.add(l2);

        Mockito.when(lineService.getLinesByUserId(1)).thenReturn(expected);

        List<Line> returned = lineService.getLinesByUserId(1);

        assertNotNull(returned);
        assertThat(returned.size(), is(2));
        assertEquals(expected, returned);
    }

    @Test(expected = UserNotFoundException.class)
    public void getLinesByUserIdError() throws UserNotFoundException {
        Mockito.when(userService.getUserById(1)).thenThrow(new UserNotFoundException());
        userService.getUserById(1);
    }

    @Test
    public void getLineById() throws LineNotFoundException {
        Line expected = new Line(1, null, null, "223-20202020", LineType.MOBILE, LineStatus.ACTIVE);

        Mockito.when(lineService.getLineById(1)).thenReturn(expected);

        Line returned = lineService.getLineById(1);

        assertNotNull(returned);
        assertEquals(expected, returned);
    }

    @Test(expected = LineNotFoundException.class)
    public void getLineByIdError() throws LineNotFoundException {
        Mockito.when(lineService.getLineById(1)).thenThrow(new LineNotFoundException());
        lineService.getLineById(1);
    }


    @Test
    public void updateLine() throws UserNotFoundException, CityNotFoundException, LineNotFoundException {
        UpdateLineDto updateLineDto = new UpdateLineDto(1,"223232", LineType.MOBILE,LineStatus.ACTIVE);

        Mockito.when(lineService.existsById(1)).thenReturn(true);


        ResponseEntity result = controller.updateLine(1,updateLineDto);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());


    }

    @Test
    public void updateLineNullCity() throws UserNotFoundException, CityNotFoundException, LineNotFoundException {
        UpdateLineDto updateLineDto = new UpdateLineDto(null,"223232", LineType.MOBILE,LineStatus.ACTIVE);

        Mockito.when(lineService.existsById(1)).thenReturn(true);


        ResponseEntity result = controller.updateLine(1,updateLineDto);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test(expected = LineNotFoundException.class)
    public void updateLineError() throws UserNotFoundException, CityNotFoundException, LineNotFoundException {
        UpdateLineDto updateLineDto = new UpdateLineDto(null,"223232", LineType.MOBILE,LineStatus.ACTIVE);
        Mockito.when(lineService.existsById(1)).thenReturn(false);
        controller.updateLine(1,updateLineDto);
    }

    @Test
    public void deleteLine() throws LineNotFoundException {
        ResponseEntity returned = controller.deleteLine(1);
        assertNotNull(returned);
        assertEquals(HttpStatus.OK,returned.getStatusCode());
    }

    @After
    public void tearDown() throws Exception {
    }

}
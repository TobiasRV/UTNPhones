package com.utn.utnphones.service;

import com.utn.utnphones.dto.LineAndQtyOfCallsDto;
import com.utn.utnphones.dto.UpdateLineDto;
import com.utn.utnphones.exceptions.LineNotFoundException;
import com.utn.utnphones.model.City;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.enums.LineStatus;
import com.utn.utnphones.model.enums.LineType;
import com.utn.utnphones.repository.LineRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockitoPostProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

public class LineServiceTest {

    LineService lineService;

    @Mock
    LineRepository lineRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        lineService = new LineService(lineRepository);
    }

    @Test
    public void getAll() {
        Line l1 = new Line(1,null,null,null,null,null);
        Line l2 = new Line(2,null,null,null,null,null);

        List<Line> expected = new ArrayList<>();
        expected.add(l1);
        expected.add(l2);

        Mockito.when(lineRepository.findAll()).thenReturn(expected);

        List<Line> returned = lineService.getAll();

        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);
    }

    @Test
    public void addLine() {
        lineService.addLine(mock(Line.class));
    }

    @Test(expected = LineNotFoundException.class)
    public void setLineStatusError() throws LineNotFoundException {
        Mockito.when(lineRepository.findById(1).orElseThrow(LineNotFoundException::new)).thenThrow(new LineNotFoundException());
        lineRepository.findById(1);
    }

    @Test(expected = LineNotFoundException.class)
    public void updateLineError() throws LineNotFoundException {
        Mockito.when(lineRepository.findById(1).orElseThrow(LineNotFoundException::new)).thenThrow(new LineNotFoundException());
        lineService.updateLine(1,mock(UpdateLineDto.class),mock(City.class));
    }

    @Test
    public void updateLine() throws LineNotFoundException {
        Line line = new Line(1,null,null,null,null,null);
        UpdateLineDto updateLineDto = new UpdateLineDto(1,"123123", LineType.MOBILE, LineStatus.ACTIVE);
        City city = new City(1,null,null,null);
        Mockito.when(lineRepository.findById(1)).thenReturn(Optional.of(line));
        lineService.updateLine(1,updateLineDto,city);
    }

    @Test
    public void getTop10Destinations() {
        Line l1 = new Line(1,null,null,null,null,null);
        Line l2 = new Line(2,null,null,null,null,null);

        List<Line> list = new ArrayList<>();
        list.add(l1);
        list.add(l2);

        List<LineAndQtyOfCallsDto> returned = new ArrayList<>();

        Mockito.when(lineRepository.getTop10Destinations(1)).thenReturn(list);
        Mockito.when(lineRepository.getQtyOfCallsToLine(1,1)).thenReturn(10);
        Mockito.when(lineRepository.getQtyOfCallsToLine(1,2)).thenReturn(20);

        for (Line l : lineRepository.getTop10Destinations(1)){
            LineAndQtyOfCallsDto dto = new LineAndQtyOfCallsDto();
            dto.setLine(l);
            dto.setQtyOfCalls(lineRepository.getQtyOfCallsToLine(1, l.getIdLine()));
            returned.add(dto);
        }

        assertThat(returned.size(), is(2));
        assertEquals(l1,returned.get(0).getLine());
        assertEquals(l2,returned.get(1).getLine());
        assertThat(returned.get(0).getQtyOfCalls(), is(10));
        assertThat(returned.get(1).getQtyOfCalls(), is(20));
    }

    @Test
    public void existsByIdTrue() {
        Mockito.when(lineRepository.existsById(1)).thenReturn(true);
        boolean returned = lineRepository.existsById(1);
        assertEquals(true,returned);
    }
    @Test
    public void existsByIdFalse() {
        Mockito.when(lineRepository.existsById(233)).thenReturn(false);
        boolean returned = lineRepository.existsById(233);
        assertEquals(false,returned);
    }

    @Test
    public void getLineById() {
        Line expected = new Line(1,null,null,null,null,null);

        Mockito.when(lineRepository.findById(1)).thenReturn(java.util.Optional.of(expected));
        Optional<Line> returned = lineRepository.findById(1);
        assertEquals(expected,returned.get());
    }

    @Test(expected = LineNotFoundException.class)
    public void getLineByIdError() throws LineNotFoundException {
        Mockito.when(lineRepository.findById(1).orElseThrow(LineNotFoundException::new)).thenThrow(new LineNotFoundException());
        lineRepository.findById(1);
    }

    @Test
    public void getLinesByUserId() {
        Line l1 = new Line(1,null,null,null,null,null);
        Line l2 = new Line(2,null,null,null,null,null);

        List<Line> expected = new ArrayList<>();
        expected.add(l1);
        expected.add(l2);

        Mockito.when(lineRepository.findByUserId(1)).thenReturn(expected);
        List<Line> returned = lineRepository.findByUserId(1);

        assertThat(returned.size(),is(2));
        assertEquals(returned, expected);
    }


    @Test(expected = LineNotFoundException.class)
    public void deleteLineError() throws LineNotFoundException {
        Mockito.when(lineRepository.findById(1).orElseThrow(LineNotFoundException::new)).thenThrow(new LineNotFoundException());
        lineRepository.findById(1);
    }

    @After
    public void tearDown() throws Exception {
    }
}
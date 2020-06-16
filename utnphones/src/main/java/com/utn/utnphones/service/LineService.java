package com.utn.utnphones.service;

import com.utn.utnphones.dto.LineAndQtyOfCallsDto;
import com.utn.utnphones.dto.UpdateLineDto;
import com.utn.utnphones.exceptions.LineNotFoundException;
import com.utn.utnphones.model.City;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.LineStatus;
import com.utn.utnphones.model.enums.LineType;
import com.utn.utnphones.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LineService {

    private LineRepository lineRepository;

    @Autowired
    public LineService(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    public List<Line> getAll() {
        return lineRepository.findAll();
    }

    public Line addLine(final Line l) {
        return lineRepository.save(l);
    }

    public void setLineStatus(Integer lineId, String action) throws Exception {
        Line l = lineRepository.findById(lineId).orElseThrow(() -> new Exception("No se encontro"));

        if (action.equalsIgnoreCase(LineStatus.ACTIVE.toString())) {
            l.setLineStatus(LineStatus.ACTIVE);
        } else if (action.equalsIgnoreCase(LineStatus.DELETED.toString())) {
            l.setLineStatus(LineStatus.DELETED);
        } else if (action.equalsIgnoreCase(LineStatus.SUSPENDED.toString())) {
            l.setLineStatus(LineStatus.SUSPENDED);
        }

        lineRepository.save(l);
    }

    public void updateLine(Integer lineId, UpdateLineDto updateLineDto, City city) throws LineNotFoundException {
        Line oldLine = lineRepository.findById(lineId).orElseThrow(LineNotFoundException::new);

        if (updateLineDto.getCityId() != null)
            oldLine.setCity(city);
        if (updateLineDto.getPhoneNumber() != null)
            oldLine.setPhoneNumber(updateLineDto.getPhoneNumber());
        if (updateLineDto.getLineType() != null)
            oldLine.setLineType(updateLineDto.getLineType());
        if (updateLineDto.getLineStatus() != null)
            oldLine.setLineStatus(updateLineDto.getLineStatus());

        lineRepository.save(oldLine);
    }

    public List<LineAndQtyOfCallsDto> getTop10Destinations(Integer userId) {

        List<LineAndQtyOfCallsDto> result = new ArrayList<>();
        for (Line l : lineRepository.getTop10Destinations(userId)) {
            LineAndQtyOfCallsDto dto = new LineAndQtyOfCallsDto();
            dto.setLine(l);
            dto.setQtyOfCalls(lineRepository.getQtyOfCallsToLine(userId, l.getIdLine()));
            result.add(dto);
        }
        return result;
    }

    public boolean existsById(Integer lineId) {
        return lineRepository.existsById(lineId);
    }

    public Line getLineById(Integer lineId) throws LineNotFoundException {
        return lineRepository.findById(lineId).orElseThrow(LineNotFoundException::new);
    }

    public List<Line> getLinesByUserId(Integer userId) {
        return lineRepository.findByUserId(userId);
    }

    public void deleteLine(Integer lineId) throws LineNotFoundException {
        Line line = lineRepository.findById(lineId).orElseThrow(LineNotFoundException::new);
        line.setLineStatus(LineStatus.DELETED);
        lineRepository.save(line);
    }
}

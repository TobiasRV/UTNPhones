package com.utn.utnphones.service;

import com.utn.utnphones.dto.LineAndQtyOfCallsDto;
import com.utn.utnphones.exceptions.LineNotFoundException;
import com.utn.utnphones.exceptions.UserNotExistsException;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.LineStatus;
import com.utn.utnphones.repository.LineRepository;
import com.utn.utnphones.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LineService {

    private LineRepository lineRepository;
    private UserRepository userRepository;

    @Autowired
    public LineService(LineRepository lineRepository, UserRepository userRepository) {
        this.lineRepository = lineRepository;
        this.userRepository = userRepository;
    }

    public List<Line> getAll() {
        return lineRepository.findAll();
    }

    public void addLine(final Line l) {
        lineRepository.save(l);
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

    public Line updateLine(Integer lineId, Line updatedLine) throws LineNotFoundException {

        lineRepository.existsById(lineId);

        Line lineToUpdate = lineRepository.findById(lineId).orElseThrow(() -> new LineNotFoundException());
        lineToUpdate = updatedLine;
        return lineRepository.save(lineToUpdate);
    }


    public List<LineAndQtyOfCallsDto> getTop10Destinations(Integer userId) throws UserNotExistsException {
        if (userRepository.existsById(userId)) {
            List<LineAndQtyOfCallsDto> result = new ArrayList<LineAndQtyOfCallsDto>();
            for (Line l : lineRepository.getTop10Destinations(userId)) {
                LineAndQtyOfCallsDto dto = new LineAndQtyOfCallsDto();
                dto.setLine(l);
                dto.setQtyOfCalls(lineRepository.getQtyOfCallsToLine(userId, l.getIdLine()));
                result.add(dto);
            }
            return result;
        } else {
            throw new UserNotExistsException();
        }
    }


    /*
    public List<LineAndQtyOfCallsDto> getTop10Destinations(Integer userId) throws UserNotExistsException {
        if (userRepository.existsById(userId)) {
            return lineRepository.getTop10Destinations(userId);
        } else {
            throw new UserNotExistsException();
        }
    }
     */
}

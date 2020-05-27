package com.utn.utnphones.service;

import com.utn.utnphones.exceptions.LineNotFoundException;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.model.enums.LineStatus;
import com.utn.utnphones.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void addLine(final Line l) {
        lineRepository.save(l);
    }

    public void setLineStatus(Integer lineId, String action) throws Exception {
        Line l = lineRepository.findById(lineId).orElseThrow(() -> new Exception("No se encontro"));

        System.out.println(action);

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
}

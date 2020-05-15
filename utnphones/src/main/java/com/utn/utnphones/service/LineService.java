package com.utn.utnphones.service;

import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineService {

    private LineRepository lineRepository;

    @Autowired
    public LineService (LineRepository lineRepository){
        this.lineRepository = lineRepository;
    }

    public List<Line> getAll(){
        return lineRepository.findAll();
    }

    public void addLine(final Line l) {
        lineRepository.save(l);
    }
}

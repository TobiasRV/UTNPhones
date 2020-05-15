package com.utn.utnphones.service;

import com.utn.utnphones.model.Call;
import com.utn.utnphones.model.City;
import com.utn.utnphones.repository.CallRepository;
import com.utn.utnphones.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallService {

    private final CallRepository callRepository;

    @Autowired
    public CallService(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    public List<Call> getAll() {
        return callRepository.findAll();
    }

    public void addCall(final Call c) {
        callRepository.save(c);
    }
}

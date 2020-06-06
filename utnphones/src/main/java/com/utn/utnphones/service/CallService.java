package com.utn.utnphones.service;

import com.utn.utnphones.dto.AddCallDto;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
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

    public void addCall(final AddCallDto c) {
        callRepository.saveNewCall(c.getOriginNumber(), c.getDestinationNumber(), c.getDate(), c.getDuration());
    }


    public List<Call> getCallsByUserAndDate(Integer userId, Date fromDate, Date toDate) {
        return callRepository.getCallsByUserAndDate(userId, fromDate, toDate);
    }

    public List<Call> getCallsByUser(Integer userId) {
        return callRepository.getCallsByUser(userId);
    }

}

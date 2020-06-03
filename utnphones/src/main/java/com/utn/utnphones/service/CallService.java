package com.utn.utnphones.service;

import com.utn.utnphones.dto.LineAndQtyOfCallsDto;
import com.utn.utnphones.exceptions.UserNotExistsException;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.repository.CallRepository;
import com.utn.utnphones.repository.UserRepository;
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

    public void addCall(final Call c) {
        callRepository.save(c);
    }


    public List<Call> getCallsByUserAndDate(Integer userId, Date fromDate, Date toDate) {
        return callRepository.getCallsByUserAndDate(userId, fromDate, toDate);
    }

    public List<Call> getCallsByUser(Integer userId) {
        return callRepository.getCallsByUser(userId);
    }

    public List<Call> getCallsByUserOverPrice(Integer userId, Float price) {
        return callRepository.getCallsByUserOverPrice(userId, price);
    }

    public Call getLongestCall(){
        return callRepository.getLongestCall();
    }
}

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
    private final UserRepository userRepository;

    @Autowired
    public CallService(CallRepository callRepository, UserRepository userRepository) {
        this.callRepository = callRepository;
        this.userRepository = userRepository;
    }

    public List<Call> getAll() {
        return callRepository.findAll();
    }

    public void addCall(final Call c) {
        callRepository.save(c);
    }


    public List<Call> getCallsByUserAndDate(Integer userId, Date fromDate, Date toDate) throws UserNotExistsException {
        if (userRepository.existsById(userId)) {
            return callRepository.getCallsByUserAndDate(userId, fromDate, toDate);
        } else {
            throw new UserNotExistsException();
        }
    }


    public List<Call> getCallsByUser(Integer userId) throws UserNotExistsException {
        if (userRepository.existsById(userId)) {
            return callRepository.getCallsByUser(userId);
        } else {
            throw new UserNotExistsException();
        }
    }
}

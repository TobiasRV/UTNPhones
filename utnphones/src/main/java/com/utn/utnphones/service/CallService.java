package com.utn.utnphones.service;

import com.utn.utnphones.dto.AddCallDto;
import com.utn.utnphones.dto.CallQueryReturnDto;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
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


    public List<CallQueryReturnDto> getCallsByUserAndDate(Integer userId, Date fromDate, Date toDate) {
        List<Call> callList = callRepository.getCallsByUserAndDate(userId, fromDate, toDate);
        List<CallQueryReturnDto> callQueryReturnDtoList = new ArrayList<>();

        //TODO checkear bill vacia
        for (Call c : callList) {
            callQueryReturnDtoList.add(
                    CallQueryReturnDto.builder().idCall(c.getIdCall()).originLine(c.getOriginLine())
                            .destinationLine(c.getDestinationLine()).callDate(c.getCallDate())
                            .idRate(c.getRate().getIdRate()).callDuration(c.getCallDuration())
                            .callCost(c.getCallCost()).callPrice(c.getCallPrice())
                            .idBill(c.getBill().getIdBill()).build());
        }

        return callQueryReturnDtoList;
    }

    public List<CallQueryReturnDto> getCallsByUser(Integer userId) {
        List<Call> callList = callRepository.getCallsByUser(userId);
        List<CallQueryReturnDto> callQueryReturnDtoList = new ArrayList<>();

        for (Call c : callList) {
            callQueryReturnDtoList.add(
                    CallQueryReturnDto.builder().idCall(c.getIdCall()).originLine(c.getOriginLine())
                            .destinationLine(c.getDestinationLine()).callDate(c.getCallDate())
                            .idRate(c.getRate().getIdRate()).callDuration(c.getCallDuration())
                            .callCost(c.getCallCost()).callPrice(c.getCallPrice())
                            .idBill(c.getBill().getIdBill()).build());
        }

        return callQueryReturnDtoList;
    }

}

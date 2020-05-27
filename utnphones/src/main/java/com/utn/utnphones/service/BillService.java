package com.utn.utnphones.service;

import com.utn.utnphones.model.Bill;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.repository.BillRepository;
import com.utn.utnphones.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public List<Bill> getAll() {
        return billRepository.findAll();
    }

    public List<Bill> getBillsByDate(Integer userId, Date fromDate, Date toDate) {
       return billRepository.getBillsByDate(userId,fromDate,toDate);
    }
}

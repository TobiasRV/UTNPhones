package com.utn.utnphones.service;

import com.utn.utnphones.exceptions.BillNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.Bill;
import com.utn.utnphones.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

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

    public List<Bill> getBillsByUserAndDate(Integer userId, Date fromDate, Date toDate) {
        return billRepository.getBillsByUserAndDate(userId, fromDate, toDate);
    }

    public List<Bill> getBillsByUser(Integer userId) {
        return billRepository.getBillsByUser(userId);
    }

    public Bill getBillById(Integer lineId, Integer billId) throws BillNotFoundException, ValidationException {
        Bill b = billRepository.findById(billId).orElseThrow(BillNotFoundException::new);

        if (!b.getLine().getIdLine().equals(lineId))
            throw new ValidationException("The bill id given does not match with the line id");

        return b;
    }

    public void payBill(Integer lineId, Integer billId) throws BillNotFoundException, ValidationException {
        Bill b = billRepository.findById(billId).orElseThrow(BillNotFoundException::new);
        if (!b.getLine().getIdLine().equals(lineId))
            throw new ValidationException("The bill id given does not match with the line id");

        b.setPaid(true);
        billRepository.save(b);
    }

}

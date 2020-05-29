package com.utn.utnphones.service;

import com.utn.utnphones.exceptions.UserNotExistsException;
import com.utn.utnphones.model.Bill;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.model.User;
import com.utn.utnphones.repository.BillRepository;
import com.utn.utnphones.repository.CallRepository;
import com.utn.utnphones.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final UserRepository userRepository;

    @Autowired
    public BillService(BillRepository billRepository, UserRepository userRepository) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
    }

    public List<Bill> getAll() {
        return billRepository.findAll();
    }

    public List<Bill> getBillsByUserAndDate(Integer userId, Date fromDate, Date toDate) throws UserNotExistsException {
        if (userRepository.existsById(userId))
            return billRepository.getBillsByUserAndDate(userId, fromDate, toDate);
        else
            throw new UserNotExistsException();

    }

    public List<Bill> getBillsByUser(Integer userId) throws UserNotExistsException {
        if (userRepository.existsById(userId)){
            return billRepository.getBillsByUser(userId);
        }else{
            throw new UserNotExistsException();
        }
    }
}

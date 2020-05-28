package com.utn.utnphones.controller;

import com.utn.utnphones.exceptions.UserNotExistsException;
import com.utn.utnphones.model.Bill;
import com.utn.utnphones.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/bill")
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/")
    public List<Bill> getAll() {
        return billService.getAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Bill>> getBillsByDate(@PathVariable Integer userId, @RequestParam Date fromDate, @RequestParam Date toDate) throws UserNotExistsException {
        List<Bill> bills = billService.getBillsByDate(userId, fromDate, toDate);

        if (bills.size() > 0)
            return ResponseEntity.ok(bills);
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}

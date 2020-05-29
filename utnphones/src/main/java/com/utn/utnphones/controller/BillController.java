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
    public ResponseEntity<List<Bill>> getBillsByUser(@PathVariable Integer userId, @RequestParam(value = "fromDate", required = false) Date fromDate, @RequestParam(value ="toDate", required = false) Date toDate) throws UserNotExistsException {

        List<Bill> lb;

        if(fromDate == null || toDate == null) {
            lb = billService.getBillsByUser(userId);
        }else {
            lb = billService.getBillsByUserAndDate(userId, fromDate, toDate);
        }
        if (lb.size() > 0)
            return ResponseEntity.ok(lb);
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}

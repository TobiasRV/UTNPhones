package com.utn.utnphones.controller;

import com.utn.utnphones.exceptions.BillNotFoundException;
import com.utn.utnphones.exceptions.LineNotFoundException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.Bill;
import com.utn.utnphones.service.BillService;
import com.utn.utnphones.service.LineService;
import com.utn.utnphones.service.UserService;
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
    private final UserService userService;
    private final LineService lineService;

    @Autowired
    public BillController(BillService billService, UserService userService, LineService lineService) {
        this.billService = billService;
        this.userService = userService;
        this.lineService = lineService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Bill>> getAll() {
        List<Bill> billList = billService.getAll();

        if (billList.size() > 0) {
            return ResponseEntity.ok(billList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Bill>> getBillsByUser(@PathVariable Integer userId, @RequestParam(value = "fromDate", required = false) Date fromDate, @RequestParam(value = "toDate", required = false) Date toDate) throws UserNotFoundException {
        if (!userService.existsById(userId))
            throw new UserNotFoundException();

        List<Bill> lb;

        if (fromDate == null || toDate == null) {
            lb = billService.getBillsByUser(userId);
        } else {
            lb = billService.getBillsByUserAndDate(userId, fromDate, toDate);
        }
        if (lb.size() > 0)
            return ResponseEntity.ok(lb);
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/{lineId}/{billId}")
    public ResponseEntity<Bill> getBillById(@PathVariable Integer lineId, @PathVariable Integer billId) throws LineNotFoundException, BillNotFoundException, ValidationException {
        if (!lineService.existsById(lineId))
            throw new LineNotFoundException();
        return ResponseEntity.ok(billService.getBillById(lineId, billId));
    }

    @PutMapping("/{lineId}/{billId}")
    public ResponseEntity payBill(@PathVariable Integer lineId, @PathVariable Integer billId) throws BillNotFoundException, LineNotFoundException, ValidationException {
        if (!lineService.existsById(lineId))
            throw new LineNotFoundException();

        billService.payBill(lineId, billId);
        return ResponseEntity.ok().build();
    }

}

package com.utn.utnphones.controller;

import com.utn.utnphones.model.Bill;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.service.BillService;
import com.utn.utnphones.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{billId}")
    public void payBill(@PathVariable Integer billId){
        billService.payBill(billId);
    }

//    @PostMapping("/")
//    public void addBill(@RequestBody Bill b) {
//        billService.addBill(b);
//    }
}

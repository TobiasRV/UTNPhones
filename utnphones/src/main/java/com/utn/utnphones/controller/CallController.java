package com.utn.utnphones.controller;

import com.utn.utnphones.dto.LineAndQtyOfCallsDto;
import com.utn.utnphones.exceptions.UserNotExistsException;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/call")
public class CallController {

    private final CallService callService;

    @Autowired
    public CallController(CallService callService) {
        this.callService = callService;
    }

    @GetMapping("/")
    public List<Call> getAll() {
        return callService.getAll();
    }

    @PostMapping("/")
    public void addCall(@RequestBody Call c) {
        callService.addCall(c);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Call>> getCallsByUser(@PathVariable Integer userId, @RequestParam(value = "fromDate", required = false) Date fromDate, @RequestParam(value = "toDate", required = false) Date toDate, @RequestParam(value = "price", required = false) Float price) throws UserNotExistsException {

        List<Call> lc = null;

        if (fromDate != null && toDate != null) {
            lc = callService.getCallsByUserAndDate(userId, fromDate, toDate);

        } else if(price != null){
            lc = callService.getCallsByUserOverPrice(userId,price);
        }else {
            lc = callService.getCallsByUser(userId);
        }
        if (lc.size() > 0) {
            return ResponseEntity.ok(lc);
        } else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}

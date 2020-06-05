package com.utn.utnphones.controller;

import com.utn.utnphones.dto.AddCallDto;
import com.utn.utnphones.exceptions.UserNotExistsException;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.model.User;
import com.utn.utnphones.service.CallService;
import com.utn.utnphones.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/call")
public class CallController {

    private final CallService callService;
    private final UserService userService;

    @Autowired
    public CallController(CallService callService, UserService userService) {
        this.callService = callService;
        this.userService = userService;
    }

    @GetMapping("/")
    public List<Call> getAll() {
        return callService.getAll();
    }

    @PostMapping("/")
    public ResponseEntity addCall(@RequestBody AddCallDto c) {
        callService.addCall(c);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Call>> getCallsByUser(@PathVariable Integer userId, @RequestParam(value = "fromDate", required = false) Date fromDate, @RequestParam(value = "toDate", required = false) Date toDate, @RequestParam(value = "price", required = false) Float price) throws UserNotExistsException {
        if (!userService.existsById(userId))
            throw new UserNotExistsException();

        List<Call> lc = null;

        if (fromDate != null && toDate != null) {
            lc = callService.getCallsByUserAndDate(userId, fromDate, toDate);

        } else if (price != null) {
            lc = callService.getCallsByUserOverPrice(userId, price);
        } else {
            lc = callService.getCallsByUser(userId);
        }
        if (lc.size() > 0) {
            return ResponseEntity.ok(lc);
        } else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

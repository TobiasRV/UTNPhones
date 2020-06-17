package com.utn.utnphones.controller;

import com.utn.utnphones.dto.AddCallDto;
import com.utn.utnphones.dto.CallQueryReturnDto;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.service.CallService;
import com.utn.utnphones.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.utn.utnphones.security.Constants.SECRET_KEY;

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Call>> getAll() {
        List<Call> callList = callService.getAll();

        if (callList.size() > 0) {
            return ResponseEntity.ok(callList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_INFRAESTRUCTURE')")
    public ResponseEntity addCall(@RequestBody AddCallDto c) {
        callService.addCall(c);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<List<CallQueryReturnDto>> getCallsByUser(@PathVariable Integer userId, @RequestParam(value = "fromDate", required = false) Date fromDate, @RequestParam(value = "toDate", required = false) Date toDate, @RequestHeader String authorization) throws UserNotFoundException {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(authorization).getBody();

        List<String> authorities = (ArrayList) claims.get("authorities");

        if (authorities.get(0).equals("ROLE_CLIENT")) {
            if (!claims.getId().equals(userId.toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        if (!userService.existsById(userId))
            throw new UserNotFoundException();

        List<CallQueryReturnDto> lc = null;

        if (fromDate != null && toDate != null) {
            lc = callService.getCallsByUserAndDate(userId, fromDate, toDate);
        } else {
            lc = callService.getCallsByUser(userId);
        }

        if (lc.size() > 0) {
            return ResponseEntity.ok(lc);
        } else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

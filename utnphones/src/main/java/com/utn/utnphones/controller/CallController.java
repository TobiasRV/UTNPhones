package com.utn.utnphones.controller;

import com.utn.utnphones.model.Call;
import com.utn.utnphones.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}

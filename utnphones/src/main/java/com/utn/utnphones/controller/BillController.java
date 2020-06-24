package com.utn.utnphones.controller;

import com.utn.utnphones.exceptions.BillNotFoundException;
import com.utn.utnphones.exceptions.LineNotFoundException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.Bill;
import com.utn.utnphones.service.BillService;
import com.utn.utnphones.service.LineService;
import com.utn.utnphones.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/bill")
@Api(value = "api/bills", produces = "application/json")
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
    @ApiOperation(value = "Get Bills", notes = "Returns all bills")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exits one bill at least"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public ResponseEntity<List<Bill>> getAll() {
        List<Bill> billList = billService.getAll();

        if (billList.size() > 0) {
            return ResponseEntity.ok(billList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT') or hasRole('ROLE_EMPLOYEE')")
    @ApiOperation(value = "Get Bills by User", notes = "Returns all bills for a specific User")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exits one bill at least"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "The id of the user", required = true),
            @ApiImplicitParam(name = "fromDate", value = "Filter: from which day to start showing"),
            @ApiImplicitParam(name = "toDate", value = "Filter: show until this day")
    })
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Bill> getBillById(@PathVariable Integer lineId, @PathVariable Integer billId) throws LineNotFoundException, BillNotFoundException, ValidationException {
        if (!lineService.existsById(lineId))
            throw new LineNotFoundException();
        return ResponseEntity.ok(billService.getBillById(lineId, billId));
    }

    @PutMapping("/{lineId}/{billId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public ResponseEntity payBill(@PathVariable Integer lineId, @PathVariable Integer billId) throws BillNotFoundException, LineNotFoundException, ValidationException {
        if (!lineService.existsById(lineId))
            throw new LineNotFoundException();

        billService.payBill(lineId, billId);
        return ResponseEntity.ok().build();
    }

}
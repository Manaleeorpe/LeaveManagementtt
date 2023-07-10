package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CurrentUserSession;
import com.example.demo.entity.LeaveBalances;
import com.example.demo.service.CurrentUserSessionService;
import com.example.demo.service.LeaveBalanceService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/leavebalances")
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;
    private final CurrentUserSessionService currentUserSessionService;

    @Autowired
    public LeaveBalanceController(LeaveBalanceService leaveBalanceService, CurrentUserSessionService currentUserSessionService) {
        this.leaveBalanceService = leaveBalanceService;
		this.currentUserSessionService = currentUserSessionService;
    }

    @GetMapping
    public ResponseEntity<List<LeaveBalances>> getAllLeaveBalances(@RequestParam(required = false) String PrivateKey) {
    	if (validatePrivateKey(PrivateKey)) {
    		List<LeaveBalances> leaveBalances = leaveBalanceService.getAllLeaveBalances();
            return ResponseEntity.ok(leaveBalances);
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    }

    @GetMapping("/{employeeid}")
    public ResponseEntity<List<LeaveBalances>> getLeaveBalanceByEmployeeId(@PathVariable Long employeeid ,@RequestParam(required = false) String PrivateKey) {
    	if (validatePrivateKey(PrivateKey)) {
    		List<LeaveBalances> leaveBalance = leaveBalanceService.getLeaveBalanceByEmployeeId(employeeid);
            return ResponseEntity.ok(leaveBalance);
    	} else {
    		 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    }
    
    public boolean validatePrivateKey( String PrivateKey) {
     	 if (PrivateKey == null) {
     	        return false; // Or handle the null case according to your requirements
     	    }
         CurrentUserSession currentUserSession = currentUserSessionService.findByPrivateKey(PrivateKey);
         boolean isOk = false;
         if(currentUserSession != null && currentUserSession.getPrivateKey().equals(PrivateKey)) {
         	isOk = true;
         }
         return isOk;
     }
}



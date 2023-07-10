package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CurrentUserSession;
import com.example.demo.entity.LeaveRequest;
import com.example.demo.service.CurrentUserSessionService;
import com.example.demo.service.LeaveRequestService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/leaverequests")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;
    private final CurrentUserSessionService currentUserSessionService;
    @Autowired
    public LeaveRequestController(LeaveRequestService leaveRequestService, CurrentUserSessionService currentUserSessionService) {
        this.leaveRequestService = leaveRequestService;
		this.currentUserSessionService = currentUserSessionService;
    }

    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequests(@RequestParam(required = false) String PrivateKey) {
    	if (validatePrivateKey(PrivateKey)) {
    		List<LeaveRequest> leaveRequests = leaveRequestService.getAllLeaveRequests();
            return ResponseEntity.ok(leaveRequests);
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
        
    }
    
    @PostMapping("/{employeeid}")
    public ResponseEntity<LeaveRequest> createLeaveRequest(@PathVariable Long employeeid,
            @RequestBody LeaveRequest leaveRequest,@RequestParam(required = false) String PrivateKey  ) {
    	if (validatePrivateKey(PrivateKey)) {
    		LeaveRequest createdLeaveRequest = leaveRequestService.createLeaveRequest(employeeid, leaveRequest);
            if (createdLeaveRequest != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(createdLeaveRequest);
            } else {
                return ResponseEntity.notFound().build();
            }
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}

    }
    
    //leavehistory
    @GetMapping("/leavehistory/{employeeid}")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsByEmployeeId(@PathVariable Long employeeid , @RequestParam(required = false) String PrivateKey ) {
    	if (validatePrivateKey(PrivateKey)) {
    		List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsByEmployeeId(employeeid);
            return ResponseEntity.ok(leaveRequests);
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    }
   //employee absenteeism
    @GetMapping("/absenteeism/{employeeid}")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsByEmployeeIdAndstatus(@PathVariable Long employeeid , @RequestParam(required = false) String PrivateKey) {
    	if (validatePrivateKey(PrivateKey)) {
    		List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsByEmployeeIdandstatus(employeeid);
            return ResponseEntity.ok(leaveRequests);
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    }
    
    //calendar
    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsByMonth(
            @PathVariable int year, @PathVariable int month, @RequestParam(required = false) String PrivateKey) {
    	if (validatePrivateKey(PrivateKey)) {
    		List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsByMonth(year, month);
            return ResponseEntity.ok(leaveRequests);
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    }
    
    @GetMapping("/{employeeid}/{year}/{month}")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsByMonthId(@PathVariable Long employeeid,
            @PathVariable int year, @PathVariable int month, @RequestParam(required = false) String PrivateKey) {
    	if (validatePrivateKey(PrivateKey)) {
    		List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsByMonthAndId(year, month, employeeid);
            return ResponseEntity.ok(leaveRequests);
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


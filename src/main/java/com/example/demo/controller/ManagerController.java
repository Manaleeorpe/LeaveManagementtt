package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CurrentUserSession;
import com.example.demo.entity.Employee;
import com.example.demo.entity.LoginRequest;
import com.example.demo.entity.Manager;
import com.example.demo.service.CurrentUserSessionService;
import com.example.demo.service.LeaveRequestService;
import com.example.demo.service.LoginService;
import com.example.demo.service.ManagerService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/managers")
public class ManagerController {
    private final ManagerService managerService;
    private final LoginService loginService;
    private final LeaveRequestService leaveRequestService;
	private final  CurrentUserSessionService currentUserSessionService;
    
    public ManagerController(ManagerService managerService, LeaveRequestService leaveRequestService, LoginService loginService, CurrentUserSessionService currentUserSessionService) {
        this.managerService = managerService;
		this.loginService = loginService;
		this.leaveRequestService = leaveRequestService;
		this.currentUserSessionService = currentUserSessionService;
    }

    @GetMapping
    public ResponseEntity<List<Manager>> getAllManagers(@RequestParam(required = false) String PrivateKey) {
    	if (validatePrivateKeyNoRole(PrivateKey)){
    		List<Manager> managers = managerService.getAllManagers();
            return ResponseEntity.ok(managers);
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
        
    }
   

    @GetMapping("/{employeeid}")
    public ResponseEntity<Manager> getManagerById(@PathVariable Long employeeid, @RequestParam(required = false) String PrivateKey) {
    	if (validatePrivateKeyNoRole(PrivateKey)){
    		Manager manager = managerService.getManagerById(employeeid);
            if (manager != null) {
                return ResponseEntity.ok(manager);
            } else {
                return ResponseEntity.notFound().build();
            }
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    }

    @PostMapping
    public ResponseEntity<Manager> createManager(@RequestBody Manager manager) {
		Manager createdManager = managerService.createManager(manager);
	    return ResponseEntity.status(HttpStatus.CREATED).body(createdManager);
    }

    @PutMapping("/{employeeid}")
    public ResponseEntity<Manager> updateManager(@PathVariable Long employeeid, @RequestBody Manager manager, @RequestParam(required = false) String PrivateKey) {
    	if (validatePrivateKey(PrivateKey, "manager")) {
    		Manager updatedManager = managerService.updateManager(employeeid, manager);
            if (updatedManager != null) {
                return ResponseEntity.ok(updatedManager);
            } else {
                return ResponseEntity.notFound().build();
            }
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    }

    @DeleteMapping("/{employeeid}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long employeeid, @RequestParam(required = false) String PrivateKey) {
    	if (validatePrivateKey(PrivateKey, "manager")) {
    		boolean deleted = managerService.deleteManager(employeeid);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    }
    
  
    
    //update the leaverequest status
    @PutMapping("/leaverequests/{leaverequestid}/{status}")
    public ResponseEntity<String> updatestatus(
            @PathVariable Long leaverequestid,
            @PathVariable String status,
            @RequestParam(required = false) String PrivateKey ) {
    	if (validatePrivateKey(PrivateKey, "manager")) {
    		 try {
    	            leaveRequestService.updatestatus(leaverequestid, status);
    	            return ResponseEntity.ok("Leave status updated successfully");
    	        } catch (IllegalArgumentException e) {
    	            return ResponseEntity.badRequest().body(e.getMessage());
    	        } catch (Exception e) {
    	            return ResponseEntity.status(500).body("An error occurred while updating leave status");
    	        }
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
       
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        
        String token = loginService.loginAccount(email, password, "manager");
        
        if (token.startsWith("Invalid")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(token);
        } else {
            return ResponseEntity.ok(token);
        }
    }
    

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam(required = false) String PrivateKey) {
        if (validatePrivateKeyNoRole(PrivateKey)){
            CurrentUserSession currentUserSession = currentUserSessionService.findByPrivateKey(PrivateKey);
            if (currentUserSession != null) {
                currentUserSessionService.deleteCurrentUserSession(currentUserSession);
                return ResponseEntity.ok("Logged out");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to logout");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Logout failed");
        }
    }
    
    public boolean validatePrivateKey( String PrivateKey, String role) {
   	 if (PrivateKey == null) {
   	        return false; // Or handle the null case according to your requirements
   	    }
       CurrentUserSession currentUserSession = currentUserSessionService.findByRoleAndPrivateKey(role, PrivateKey);
       boolean isOk = false;
       if(currentUserSession != null && currentUserSession.getPrivateKey().equals(PrivateKey) && currentUserSession.getRole().equals(role)) {
       	isOk = true;
       }
       return isOk;
   }
    public boolean validatePrivateKeyNoRole(String PrivateKey) {
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

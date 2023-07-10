package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.CurrentUserSession;
import com.example.demo.service.CurrentUserSessionService;

@RestController
@RequestMapping("/sessions")
public class CurrentUserSessionController {

    private final CurrentUserSessionService currentUserSessionService;

    @Autowired
    public CurrentUserSessionController(CurrentUserSessionService currentUserSessionService) {
        this.currentUserSessionService = currentUserSessionService;
    }

    @GetMapping("/{role}/{sessionid}")
    public ResponseEntity<CurrentUserSession> getCurrentUserSessionByRoleAndId(@PathVariable String role,
                                                                               @PathVariable Integer sessionid) {
        CurrentUserSession currentUserSession = currentUserSessionService.getCurrentUserSessionByRoleAndId(role, sessionid);
        if (currentUserSession != null) {
            return ResponseEntity.ok(currentUserSession);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

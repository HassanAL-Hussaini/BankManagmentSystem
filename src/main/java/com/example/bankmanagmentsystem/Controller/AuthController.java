package com.example.bankmanagmentsystem.Controller;

import com.example.bankmanagmentsystem.Model.User;
import com.example.bankmanagmentsystem.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    public ResponseEntity<?> getUsers(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(authService.getUsers(user.getId()));
    }

}

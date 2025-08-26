package com.example.bankmanagmentsystem.Service;

import com.example.bankmanagmentsystem.Model.User;
import com.example.bankmanagmentsystem.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
// for Admins Only from here Admins see all the users Customer and Employee
    private final AuthRepository authRepository;
    public List<User> getUsers(Integer adminId){
        return authRepository.findAll();
    }
}

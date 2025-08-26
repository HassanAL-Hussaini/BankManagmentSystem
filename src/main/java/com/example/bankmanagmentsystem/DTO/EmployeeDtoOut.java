package com.example.bankmanagmentsystem.DTO;

import com.example.bankmanagmentsystem.Model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeDtoOut {
    private String name;
    private String email;
    private String role;

    private String position;
    private Double salary;
}

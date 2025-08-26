package com.example.bankmanagmentsystem.DTO;

import com.example.bankmanagmentsystem.Model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
public class CustomerDtoOut {
    private String name;
    private String email;
    private String role;
    private Set<Account> accounts;
    private String phonNumber;

}

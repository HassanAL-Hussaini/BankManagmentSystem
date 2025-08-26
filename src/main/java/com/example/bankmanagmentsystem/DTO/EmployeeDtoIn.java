package com.example.bankmanagmentsystem.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDtoIn {
    @NotEmpty
    @Size(min = 4,max = 10 , message = "Length of the username must bbe between 4 and 10")
    private String username;

    @NotEmpty
    @Size(min = 6,message = "password should be at least 6 Characters")
    private String password;

    @Size(min = 2,max = 20 , message = "Length of the username must bbe between 2 and 20")
    @NotEmpty
    private String name;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String position;

    @NotNull
    @Positive
    private Double salary;

}

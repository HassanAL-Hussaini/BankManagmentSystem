package com.example.bankmanagmentsystem.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDtoIn {

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
    @Size(min = 10,max = 10 , message = "should start with 05 and must be 10 numbers only")
    @Pattern(regexp = "^05\\d{8}$" , message = "it should start with 05")
    private String phoneNumber;

}

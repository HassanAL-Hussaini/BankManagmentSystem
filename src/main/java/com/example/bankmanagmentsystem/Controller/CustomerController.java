package com.example.bankmanagmentsystem.Controller;

import com.example.bankmanagmentsystem.API.ApiResponse;
import com.example.bankmanagmentsystem.DTO.CustomerDtoIn;
import com.example.bankmanagmentsystem.Model.Customer;
import com.example.bankmanagmentsystem.Model.User;
import com.example.bankmanagmentsystem.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getCustomer(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(customerService.getCustomerByUserId(user.getId()));
    }

    //Auth : Customer Only
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CustomerDtoIn customerDtoIn){
        customerService.register(customerDtoIn);
        return ResponseEntity.status(200).body(new ApiResponse("Customer Register Successfully"));
    }

    //Auth : customer
    @PutMapping()
    public ResponseEntity<?> update(@AuthenticationPrincipal User user,
                                    @RequestBody @Valid CustomerDtoIn customerDtoIn){
        customerService.update(user.getId(),customerDtoIn);
        return ResponseEntity.status(200).body(new ApiResponse("Customer updated Successfully"));

    }

    //Delete handles By Admins Only
    @DeleteMapping("{deletedUserId}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user , @PathVariable Integer deletedUserId){
        customerService.delete(deletedUserId);
        return ResponseEntity.status(200).body(new ApiResponse("Customer Deleted Successfully"));
    }







}

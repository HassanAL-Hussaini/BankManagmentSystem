package com.example.bankmanagmentsystem.Controller;

import com.example.bankmanagmentsystem.API.ApiResponse;
import com.example.bankmanagmentsystem.DTO.EmployeeDtoIn;
import com.example.bankmanagmentsystem.DTO.EmployeeDtoOut;
import com.example.bankmanagmentsystem.Model.User;
import com.example.bankmanagmentsystem.Repository.EmployeeRepository;
import com.example.bankmanagmentsystem.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    //Admin & Employee
    public ResponseEntity<?> getEmployeeByLogin(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(employeeService.getEmployeeById(user.getId()));
    }
    //Only Admin can Register the Employee
    @PostMapping
    public ResponseEntity<?> registerEmployee(@AuthenticationPrincipal User user,
                                              @RequestBody @Valid EmployeeDtoIn employeeDtoIn){
        employeeService.registerEmployee(employeeDtoIn);
        return ResponseEntity.status(200).body(new ApiResponse("Employee added by Admin Successfully"));
    }
    @PutMapping
    //Employee only
    public ResponseEntity<?> updateEmployee(@AuthenticationPrincipal User user,
                                            @RequestBody @Valid EmployeeDtoIn employeeDtoIn){
        employeeService.update(user.getId(),employeeDtoIn);
        return ResponseEntity.status(200).body(new ApiResponse("Employee Updated Successfully"));
    }
    //Only Admin can delete Employee
    @DeleteMapping("{deletedEmployeeId}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user,
                                    @PathVariable Integer deletedEmployeeId){
        employeeService.deleteEmployee(deletedEmployeeId);
        return ResponseEntity.status(200).body(new ApiResponse("Deleted Successfully"));
    }

}

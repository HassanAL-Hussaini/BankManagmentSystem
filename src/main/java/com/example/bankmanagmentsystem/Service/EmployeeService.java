package com.example.bankmanagmentsystem.Service;

import com.example.bankmanagmentsystem.API.ApiException;
import com.example.bankmanagmentsystem.DTO.EmployeeDtoIn;
import com.example.bankmanagmentsystem.DTO.EmployeeDtoOut;
import com.example.bankmanagmentsystem.Model.Employee;
import com.example.bankmanagmentsystem.Model.User;
import com.example.bankmanagmentsystem.Repository.AuthRepository;
import com.example.bankmanagmentsystem.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final AuthRepository authRepository;
    private final EmployeeRepository employeeRepository;

    //Employee & Admin
    public EmployeeDtoOut getEmployeeById(Integer userId){
        User user = authRepository.findUserById(userId);


        if(user == null){
            throw new ApiException("Authentication Required please login to access Employee Information");
        }

        Employee employee =  employeeRepository.findEmployeeByUser(user);

        return new EmployeeDtoOut(
                user.getName(),
                user.getEmail(),
                user.getRole(),
                employee.getPosition(),
                employee.getSalary());
    }

    //employee register by Admin.
    public void registerEmployee(EmployeeDtoIn employeeDtoIn) {
        User user = new User();
        if (authRepository.existsByUsername(employeeDtoIn.getUsername())) {
            throw new ApiException("Username is already taken");
        }
        if (authRepository.existsByEmail(employeeDtoIn.getEmail())) {
            throw new ApiException("Email is already registered");
        }
        user.setRole("EMPLOYEE");
        user.setPassword(hashPass(employeeDtoIn.getPassword()));
        user.setName(employeeDtoIn.getName());
        user.setEmail(employeeDtoIn.getEmail());
        user.setUsername(employeeDtoIn.getUsername());
        authRepository.save(user);

        Employee employee = new Employee();
        employee.setPosition(employeeDtoIn.getPosition());
        employee.setSalary(employeeDtoIn.getSalary());
        employee.setUser(user);

        employeeRepository.save(employee);
    }
    //Employee
    public void update(Integer userId,EmployeeDtoIn employeeDtoIn){
        User oldUser = authRepository.findUserById(userId);

        if(oldUser == null){
            throw new ApiException("Authentication Required please login to access Employee Information");
        }

        oldUser.setUsername(employeeDtoIn.getUsername());
        oldUser.setPassword(hashPass(employeeDtoIn.getPassword()));
        oldUser.setName(employeeDtoIn.getName());
        oldUser.setEmail(employeeDtoIn.getEmail());

        authRepository.save(oldUser);

        Employee employee = employeeRepository.findEmployeeByUser(oldUser);
        employee.setPosition(employeeDtoIn.getPosition());
        employee.setSalary(employeeDtoIn.getSalary());
//        employee.setUser(oldUser); //TODO : this line cuz the Error of ({
//    "message": "A different object with the same identifier value was already associated with the session: [com.example.bankmanagmentsystem.Model.Employee#3]"
//}) in the Postman

        employeeRepository.save(employee);
    }

    public void deleteEmployee(Integer userId){
        User user = authRepository.findUserById(userId);
        if(user == null) {
            throw new ApiException("Authentication Required please login to access Employee Information");
        }
        authRepository.delete(user);
    }
    private String hashPass(String originalPass){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(originalPass);
    }



}

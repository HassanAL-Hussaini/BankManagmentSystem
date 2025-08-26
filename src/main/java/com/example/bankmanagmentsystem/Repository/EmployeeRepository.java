package com.example.bankmanagmentsystem.Repository;

import com.example.bankmanagmentsystem.Model.Employee;
import com.example.bankmanagmentsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Employee findEmployeeById(Integer id);
    Employee findEmployeeByUser(User user);
    Employee getEmployeeByUser(User user);

}

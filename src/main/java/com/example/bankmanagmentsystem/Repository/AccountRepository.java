package com.example.bankmanagmentsystem.Repository;

import com.example.bankmanagmentsystem.Model.Account;
import com.example.bankmanagmentsystem.Model.Customer;
import com.example.bankmanagmentsystem.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    List<Account> getAccountByEmployee(Employee employee);

    List<Account> getAccountByCustomer(Customer customer);

    Account findAccountByEmployee(Employee employee);

    Account findAccountByCustomer(Customer customer);

    Account findAccountById(Integer id);
}

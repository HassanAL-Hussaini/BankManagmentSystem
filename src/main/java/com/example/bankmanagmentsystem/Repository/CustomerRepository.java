package com.example.bankmanagmentsystem.Repository;

import com.example.bankmanagmentsystem.Model.Customer;
import com.example.bankmanagmentsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findCustomerById(Integer id);

    Customer findCustomerByUser(User user);
    boolean existsByPhoneNumber(String phoneNumber);

    Customer getCustomerById(Integer id);
    Customer getCustomerByUser(User user);
}

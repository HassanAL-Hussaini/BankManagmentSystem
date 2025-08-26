package com.example.bankmanagmentsystem.Service;

import com.example.bankmanagmentsystem.API.ApiException;
import com.example.bankmanagmentsystem.DTO.CustomerDtoIn;
import com.example.bankmanagmentsystem.DTO.CustomerDtoOut;
import com.example.bankmanagmentsystem.Model.Account;
import com.example.bankmanagmentsystem.Model.Customer;
import com.example.bankmanagmentsystem.Model.User;
import com.example.bankmanagmentsystem.Repository.AuthRepository;
import com.example.bankmanagmentsystem.Repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public CustomerDtoOut getCustomerByUserId(Integer userId){
        User user = authRepository.findUserById(userId);
        Customer customer = customerRepository.findCustomerByUser(user);
        if(customer == null || user == null){
            throw new ApiException("customer Not Found");
        }
        return new CustomerDtoOut(
                user.getName(),
                user.getEmail(),
                user.getRole(),
                customer.getAccounts(),
                customer.getPhoneNumber()
        );
    }

//    @Transactional//if anything gose Wrong this method will Recall back everything step by step
    public void register(CustomerDtoIn customerDtoIn){
        User user = new User();
        // check duplicates
        if (authRepository.existsByUsername(customerDtoIn.getUsername())) {
            throw new ApiException("Username is already taken");
        }
        if (authRepository.existsByEmail(customerDtoIn.getEmail())) {
            throw new ApiException("Email is already registered");
        }
        if (customerRepository.existsByPhoneNumber(customerDtoIn.getPhoneNumber())) {
            throw new ApiException("Phone number is already used");
        }
        //Default Values User can't edit it -> once he clicks the Customer endpoint this comes with it.
        user.setRole("CUSTOMER");
        user.setUsername(customerDtoIn.getUsername());
        user.setPassword(hashPass(customerDtoIn.getPassword()));//encrypt the password
        user.setEmail(customerDtoIn.getEmail());
        user.setName(customerDtoIn.getName());
        authRepository.save(user);

        Customer customer = new Customer();
        customer.setPhoneNumber(customerDtoIn.getPhoneNumber());
        customer.setUser(user);
        customerRepository.save(customer);

    }

    public void update(Integer userId ,CustomerDtoIn newCustomerDtoIn){
        User oldUser = authRepository.findUserById(userId);
        if(oldUser == null){
            throw new ApiException("user not found");
        }
        Customer oldCustomer = customerRepository.findCustomerByUser(oldUser);
        if(oldCustomer == null){
            throw new ApiException("Customer not Found");
        }

        oldUser.setUsername(newCustomerDtoIn.getUsername());
        oldUser.setName(newCustomerDtoIn.getName());
        oldUser.setEmail(newCustomerDtoIn.getEmail());
        oldUser.setPassword(hashPass(newCustomerDtoIn.getPassword()));
        authRepository.save(oldUser);

        oldCustomer.setPhoneNumber(newCustomerDtoIn.getPhoneNumber());
        customerRepository.save(oldCustomer);
    }

    //from Admins only
    public void delete(Integer userId){
        User user = authRepository.findUserById(userId);
        if(user == null){
            throw new ApiException("Authentication required to delete Customer Account");
        }
        authRepository.delete(user);
    }

    private String hashPass(String originalPass){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(originalPass);
    }
}

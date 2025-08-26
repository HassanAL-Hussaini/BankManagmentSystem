package com.example.bankmanagmentsystem.Service;

import com.example.bankmanagmentsystem.API.ApiException;
import com.example.bankmanagmentsystem.Model.Account;
import com.example.bankmanagmentsystem.Model.Customer;
import com.example.bankmanagmentsystem.Model.Employee;
import com.example.bankmanagmentsystem.Model.User;
import com.example.bankmanagmentsystem.Repository.AccountRepository;
import com.example.bankmanagmentsystem.Repository.AuthRepository;
import com.example.bankmanagmentsystem.Repository.CustomerRepository;
import com.example.bankmanagmentsystem.Repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AuthRepository authRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;


    //5. List user's accounts
    public List<Account> getAllAccount(Integer userId){
        User user = authRepository.findUserById(userId);
        if(user == null) {
            throw new ApiException("Authentication required. Please log in to access this resource");
        }
        List<Account> accountList = new ArrayList<>();
        Employee employee = employeeRepository.getEmployeeByUser(user);
        Customer customer = customerRepository.getCustomerByUser(user);
        if(employee != null){
            accountList = accountRepository.getAccountByEmployee(employee);
        }
        if(customer != null){
            accountList = accountRepository.getAccountByCustomer(customer);
        }



        return accountList;
    }

    public void addAccount(Integer userId, Account account){
        User user = authRepository.findUserById(userId);

        if(user == null){
            throw new ApiException("Authentication failed. Unable to verify user identity");
        }
        Employee employee = employeeRepository.getEmployeeByUser(user);
        Customer customer = customerRepository.getCustomerByUser(user);

        if(employee != null){
            account.setEmployee(employee);
            account.setIsActive(false);
            accountRepository.save(account);
        }
        if(customer != null){
            account.setCustomer(customer);
            account.setIsActive(false);
            accountRepository.save(account);
        }
    }

    public void updateAccount(Integer userId, Integer accountId,Account account){
        User existingUser = authRepository.findUserById(userId);
        Account oldAccount = accountRepository.findAccountById(accountId);
        if(existingUser == null){
            throw new ApiException("Authentication required to perform this operation");
        }
        if(oldAccount == null){
            throw new ApiException("Account not found or does not exist");
        }


        boolean isAuthorized = false;

        if(oldAccount.getEmployee() != null && oldAccount.getEmployee().getId().equals(userId)) {
            isAuthorized = true;
        }

        if(oldAccount.getCustomer() != null && oldAccount.getCustomer().getId().equals(userId)) {
            isAuthorized = true;
        }

        if(!isAuthorized) {
            throw new ApiException("Access denied. You do not have permission to modify this account");
        }

        oldAccount.setAccountNumber(account.getAccountNumber());
        oldAccount.setBalance(account.getBalance());
        accountRepository.save(oldAccount);

    }

    public void deleteAccount(Integer userId, Integer accountId){
        User existingUser = authRepository.findUserById(userId);
        Account account = accountRepository.findAccountById(accountId);
        if(existingUser == null){
            throw new ApiException("Authentication required to perform this operation");
        }
        if(account == null){
            throw new ApiException("Account not found or does not exist");
        }

        boolean isAuthorized = false;

        if(account.getEmployee() != null && account.getEmployee().getId().equals(userId)) {
            isAuthorized = true;
        }

        if(account.getCustomer() != null && account.getCustomer().getId().equals(userId)) {
            isAuthorized = true;
        }

        if(!isAuthorized) {
            throw new ApiException("Access denied. You do not have permission to delete this account");
        }
        accountRepository.delete(account);

    }

    //4. View account details
    public Account getAccountDetails(Integer userId, Integer accountId) {
        User user = authRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("Authentication required. Please log in to access account details");
        }

        Account account = accountRepository.findAccountById(accountId);

        if (account == null) {
            throw new ApiException("Account not found or does not exist");
        }

        boolean hasPermission = false;

        if (account.getEmployee() != null && account.getEmployee().getId().equals(userId)) {
            hasPermission = true;
        }

        if (account.getCustomer() != null && account.getCustomer().getId().equals(userId)) {
            hasPermission = true;
        }

        if (!hasPermission) {
            throw new ApiException("Access denied. You do not have permission to view this account");
        }

        return account;
    }

    //3. Active a bank account
    public void activateAccount(Integer userId, Integer accountId) {
        User user = authRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("Authentication required. Please log in to activate account");
        }

        Account account = accountRepository.findAccountById(accountId);

        if (account == null) {
            throw new ApiException("Account not found or does not exist");
        }

        boolean hasPermission = false;

        if (account.getEmployee() != null && account.getEmployee().getId().equals(userId)) {
            hasPermission = true;
        }

        if (account.getCustomer() != null && account.getCustomer().getId().equals(userId)) {
            hasPermission = true;
        }

        if (!hasPermission) {
            throw new ApiException("Access denied. You do not have permission to activate this account");
        }

        if (Boolean.TRUE.equals(account.getIsActive())) {
            throw new ApiException("Account is already active");
        }

        account.setIsActive(true);
        accountRepository.save(account);
    }
    //6. Deposit and withdraw money
    public void depositMoney(Integer userId, Integer accountId, BigDecimal amount) {
        User user = authRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("Authentication required. Please log in to perform transactions");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException("Deposit amount must be greater than zero");
        }

        Account account = accountRepository.findAccountById(accountId);

        if (account == null) {
            throw new ApiException("Account not found");
        }

        boolean isOwner = false;
        if (account.getEmployee() != null && account.getEmployee().getId().equals(userId)) {
            isOwner = true;
        }
        if (account.getCustomer() != null && account.getCustomer().getId().equals(userId)) {
            isOwner = true;
        }

        if (!isOwner) {
            throw new ApiException("Access denied. You can only deposit to your own accounts");
        }

        if (!Boolean.TRUE.equals(account.getIsActive())) {
            throw new ApiException("Cannot deposit to inactive account. Please activate your account first");
        }

        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }
    //withdraw money
    public void withdrawMoney(Integer userId, Integer accountId, BigDecimal amount) {
        User user = authRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("Authentication required. Please log in to perform transactions");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException("Withdrawal amount must be greater than zero");
        }

        Account account = accountRepository.findAccountById(accountId);

        if (account == null) {
            throw new ApiException("Account not found");
        }

        boolean isOwner = false;
        if (account.getEmployee() != null && account.getEmployee().getId().equals(userId)) {
            isOwner = true;
        }
        if (account.getCustomer() != null && account.getCustomer().getId().equals(userId)) {
            isOwner = true;
        }

        if (!isOwner) {
            throw new ApiException("Access denied. You can only withdraw from your own accounts");
        }

        if (!Boolean.TRUE.equals(account.getIsActive())) {
            throw new ApiException("Cannot withdraw from inactive account. Please activate your account first");
        }

        // Check sufficient funds
        if (account.getBalance().compareTo(amount) < 0) {
            throw new ApiException("Insufficient funds. Available balance: " + account.getBalance());
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }
    public void transferFunds(Integer userId, Integer fromAccountId, Integer toAccountId, BigDecimal amount) {
        User user = authRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("Authentication required. Please log in to perform transactions");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException("Transfer amount must be greater than zero");
        }

        if (fromAccountId.equals(toAccountId)) {
            throw new ApiException("Cannot transfer funds to the same account");
        }

        Account fromAccount = accountRepository.findAccountById(fromAccountId);
        Account toAccount = accountRepository.findAccountById(toAccountId);

        if (fromAccount == null || toAccount == null) {
            throw new ApiException("One or both accounts not found");
        }

        boolean isOwner = false;
        if (fromAccount.getEmployee() != null && fromAccount.getEmployee().getId().equals(userId)) {
            isOwner = true;
        }
        if (fromAccount.getCustomer() != null && fromAccount.getCustomer().getId().equals(userId)) {
            isOwner = true;
        }

        if (!isOwner) {
            throw new ApiException("Access denied. You can only transfer from your own accounts");
        }

        if (!Boolean.TRUE.equals(fromAccount.getIsActive())) {
            throw new ApiException("Cannot transfer from inactive account");
        }
        if (!Boolean.TRUE.equals(toAccount.getIsActive())) {
            throw new ApiException("Cannot transfer to inactive account");
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new ApiException("Insufficient funds for transfer. Available balance: " + fromAccount.getBalance());
        }

        BigDecimal fromNewBalance = fromAccount.getBalance().subtract(amount);
        BigDecimal toNewBalance = toAccount.getBalance().add(amount);

        fromAccount.setBalance(fromNewBalance);
        toAccount.setBalance(toNewBalance);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    public void blockAccount(Integer userId, Integer accountId) {
        User user = authRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("Authentication required. Please log in to manage accounts");
        }

        Account account = accountRepository.findAccountById(accountId);

        if (account == null) {
            throw new ApiException("Account not found");
        }

        boolean isOwner = false;
        if (account.getEmployee() != null && account.getEmployee().getId().equals(userId)) {
            isOwner = true;
        }
        if (account.getCustomer() != null && account.getCustomer().getId().equals(userId)) {
            isOwner = true;
        }

        if (!isOwner) {
            throw new ApiException("Access denied. You can only block your own accounts");
        }

        if (!Boolean.TRUE.equals(account.getIsActive())) {
            throw new ApiException("Account is already blocked");
        }

        account.setIsActive(false);
        accountRepository.save(account);
    }
}

package com.example.bankmanagmentsystem.Controller;


import com.example.bankmanagmentsystem.API.ApiResponse;
import com.example.bankmanagmentsystem.Model.Account;
import com.example.bankmanagmentsystem.Model.User;
import com.example.bankmanagmentsystem.Service.AccountService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    //5. List user's accounts
    @GetMapping("/get")
    public ResponseEntity<?> getAccounts(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(accountService.getAllAccount(user.getId()));
    }
    @PostMapping("/add")
    public ResponseEntity<?> addAccount(@AuthenticationPrincipal User user, @Valid @RequestBody Account account){
        accountService.addAccount(user.getId(),account);
        return ResponseEntity.status(200).body(new ApiResponse("Account Added Successfully"));
    }
    @PutMapping("/update/{accountId}")
    public ResponseEntity<?> updateAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @Valid @RequestBody Account account){
        accountService.updateAccount(user.getId(),accountId,account);
        return ResponseEntity.status(200).body(new ApiResponse("Account Updated Successfully"));
    }
    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId){
        accountService.deleteAccount(user.getId(),accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account Deleted Successfully"));
    }
    //4. View account details
    @GetMapping("/get-details/{accountId}")
    public ResponseEntity<?> getAccountDetails(@AuthenticationPrincipal User user,@PathVariable Integer accountId) {
        return ResponseEntity.status(200).body(accountService.getAccountDetails(user.getId(), accountId));
    }
    //3. Active a bank account
    @PutMapping("/activate/{accountId}")
    public ResponseEntity<?> activateAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        accountService.activateAccount(user.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account activated successfully"));
    }
    // Deposit
    @PostMapping("/deposit/{accountId}/{amount}")
    public ResponseEntity<?> deposit(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @PathVariable BigDecimal amount) {
        accountService.depositMoney(user.getId(), accountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Deposit successful"));
    }

    // Withdraw
    @PostMapping("/withdraw/{accountId}/{amount}")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @PathVariable BigDecimal amount) {
        accountService.withdrawMoney(user.getId(), accountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Withdrawal successful"));
    }

    // Transfer
    @PostMapping("/transfer/{fromAccountId}/{toAccountId}/{amount}")
    public ResponseEntity<?> transfer(@AuthenticationPrincipal User user, @PathVariable Integer fromAccountId, @PathVariable Integer toAccountId, @PathVariable BigDecimal amount) {
        accountService.transferFunds(user.getId(), fromAccountId, toAccountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Transfer successful"));
    }

    // Block account
    @PutMapping("/block/{accountId}")
    public ResponseEntity<?> blockAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        accountService.blockAccount(user.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account blocked successfully"));
    }
}

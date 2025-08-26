package com.example.bankmanagmentsystem.Service;

import com.example.bankmanagmentsystem.API.ApiException;
import com.example.bankmanagmentsystem.Model.User;
import com.example.bankmanagmentsystem.Repository.AuthRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findUserByUsername(username);
        if(user == null){
            throw new ApiException("Wrong Username or Password");
        }
        return user;
    }
}

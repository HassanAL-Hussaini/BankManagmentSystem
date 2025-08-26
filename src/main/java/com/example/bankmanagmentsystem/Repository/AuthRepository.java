package com.example.bankmanagmentsystem.Repository;

import com.example.bankmanagmentsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRepository extends JpaRepository<User,Integer> {
    User findUserById(Integer id);

    User findUserByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    List<User> findUserByRole(String role);
}

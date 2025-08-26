package com.example.bankmanagmentsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotEmpty
//    @Size(min = 4,max = 10 , message = "Length of the username must bbe between 4 and 10")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String username;

//    @NotEmpty
//    @Size(min = 6,message = "password should be at least 6 Characters")
    @Column(columnDefinition = "varchar(255) not null")
    private String password;

//    @Size(min = 2,max = 20 , message = "Length of the username must bbe between 2 and 20")
//    @NotEmpty
    private String name;

//    @Email
//    @NotEmpty
    private String email;

    @Pattern(regexp = "^(CUSTOMER|EMPLOYEE|ADMIN)$")
    @Column(columnDefinition = "varchar(10) not null")
    private String role;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Customer customer;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Employee employee;

    // UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //this is for check every one dose he have the authority to go inside the endpoint.
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getUsername() {
        return this.username; // رجّعتها صح بدل ما كانت فاضية
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.example.bankmanagmentsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Account number cannot be null")
    @Pattern(
            regexp = "^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$",
            message = "Account number must follow the format XXXX-XXXX-XXXX-XXXX"
    )
    @Column(columnDefinition = "VARCHAR(19) NOT NULL")
    private String accountNumber;

    @NotNull(message = "Balance cannot be null")
    @PositiveOrZero(message = "Balance must be zero or positive")
    @Column(columnDefinition = "DECIMAL(15,2) NOT NULL")
    private BigDecimal balance;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean isActive;

    @ManyToOne
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @JsonIgnore
    private Customer customer;


}

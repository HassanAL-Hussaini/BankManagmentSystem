package com.example.bankmanagmentsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotEmpty
    @Column(columnDefinition = "varchar(50) not null")
    private String position;

//    @NotNull
//    @Positive
    @Column(columnDefinition = "double not null")
    private Double salary;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;
}

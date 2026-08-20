package com.example.UserRegistration.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "User name is required!")
    @Size(
            min = 2,
            max = 20,
            message = "Username must be between 2 and 20 characters"
    )
    private String userName;

    @NotBlank(message = "Password is required!")
    @Size(
            min = 8,
            max = 30,
            message = "Password must be between 8 and 30 characters"
    )
    private String password;

    @NotBlank(message = "Email is required!")
    @Email(message = "Please enter a valid email address")
    private String email;
}
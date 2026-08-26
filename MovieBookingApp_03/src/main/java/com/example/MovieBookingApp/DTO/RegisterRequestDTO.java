package com.example.MovieBookingApp.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;
}

package com.example.MovieBookingApp.controller;

import com.example.MovieBookingApp.DTO.LoginRequestDTO;
import com.example.MovieBookingApp.DTO.LoginResponseDTO;
import com.example.MovieBookingApp.DTO.RegisterRequestDTO;
import com.example.MovieBookingApp.entity.User;
import com.example.MovieBookingApp.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

   @Autowired
   private AuthenticationService authenticationService;

    @PostMapping("/registernormaluser")
    public ResponseEntity<User> registerNormalUser(@RequestBody RegisterRequestDTO  registerRequestDTO) {
         return ResponseEntity.ok(authenticationService.registerNormalUser(registerRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
      return  ResponseEntity.ok(authenticationService.login(loginRequestDTO));
    }
}

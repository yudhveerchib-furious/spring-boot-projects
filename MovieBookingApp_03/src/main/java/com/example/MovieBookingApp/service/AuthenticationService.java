package com.example.MovieBookingApp.service;

import com.example.MovieBookingApp.DTO.LoginRequestDTO;
import com.example.MovieBookingApp.DTO.LoginResponseDTO;
import com.example.MovieBookingApp.DTO.RegisterRequestDTO;
import com.example.MovieBookingApp.JWT.JwtService;
import com.example.MovieBookingApp.Repository.UserRepo;
import com.example.MovieBookingApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public User registerNormalUser(RegisterRequestDTO registerRequestDTO) {
      if(userRepo.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
          throw new RuntimeException("Username already exists");
      }
      Set<String> roles = new HashSet<>();
      roles.add("ROLE_USER");

      User user = new User();
      user.setUsername(registerRequestDTO.getUsername());
      user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
      user.setEmail(registerRequestDTO.getEmail());
      user.setRoles(roles);

      return  userRepo.save(user);
    }

    public User registerAdminUser(RegisterRequestDTO registerRequestDTO) {
        if(userRepo.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setEmail(registerRequestDTO.getEmail());
        user.setRoles(roles);

        return  userRepo.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepo.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Username not found"));

         authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(
                         loginRequestDTO.getUsername(),
                         loginRequestDTO.getPassword()
                 )
         );

         String token = jwtService.generateToken(user);
         return LoginResponseDTO.builder().jwtToken(token).username(user.getUsername()).
                 roles(user.getRoles()).build();
    }
}


package com.example.UserRegistration.service;

import com.example.UserRegistration.entity.User;
import com.example.UserRegistration.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

     @Autowired
     UserRepo userRepo;

     public void saveUser(User user) {
         userRepo.save(user);
     }

}

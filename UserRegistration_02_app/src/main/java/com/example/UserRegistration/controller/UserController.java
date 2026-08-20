package com.example.UserRegistration.controller;


import com.example.UserRegistration.entity.User;
import com.example.UserRegistration.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user",new User());
        return "userregistraion";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") @Valid User user,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "userregistraion";
        }

        userService.saveUser(user);

        return "success";
    }

}

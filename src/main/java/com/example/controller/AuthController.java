// File: src/main/java/com/example/controller/AuthController.java
package com.example.controller;

import com.example.dao.UserDao;
import com.example.model.Role;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Return login.html
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup"; // Return signup.html
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute User user, @RequestParam("role") String role, RedirectAttributes redirectAttributes) {
        // IMPORTANT: Hash the password before saving it to the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.valueOf(role));

        userDao.save(user);

        redirectAttributes.addFlashAttribute("successMessage", "Signup successful! Please log in.");
        return "redirect:/login";
    }
}
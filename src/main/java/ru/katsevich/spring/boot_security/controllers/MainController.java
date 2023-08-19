package ru.katsevich.spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.katsevich.spring.boot_security.entities.User;
import ru.katsevich.spring.boot_security.repository.RoleRepository;
import ru.katsevich.spring.boot_security.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.Set;


@Controller
public class MainController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/user")
    public String userPage(Model model, Principal principal, Authentication authentication) {
        User user = userRepository.findByUsername(principal.getName());
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal) {
        List<User> users = userRepository.findAll();
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("thisUser", user);
        model.addAttribute("allUsers", users);
        return "adminPage";
    }

}

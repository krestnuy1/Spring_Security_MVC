package ru.katsevich.spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.katsevich.spring.boot_security.entities.Role;
import ru.katsevich.spring.boot_security.entities.User;
import ru.katsevich.spring.boot_security.repository.RoleRepository;
import ru.katsevich.spring.boot_security.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "addOrUpdateUser";
    }


    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userID") Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping()
    public String saveUserByAdmin(@ModelAttribute("user") User user,
                                  BindingResult result,
                                  @RequestParam("selectedRoles") List<Long> selectedRoleIds,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("allRoles", roleRepository.findAll());
            return "addOrUpdateUser";
        }
        Set<Role> selectedRoles = new HashSet<>();
        for (Long roleId : selectedRoleIds) {
            Role role = roleRepository.findById(roleId).orElse(null);
            if (role != null) {
                selectedRoles.add(role);
            }
        }

        user.setRoles(selectedRoles);
        userRepository.save(user);

        return "redirect:/admin";
    }



    @GetMapping("/updateUser")
    public String updateUser(Model model, @RequestParam("userID") Long id) {
        User user = userRepository.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleRepository.findAll());
        return "addOrUpdateUser";

    }

}

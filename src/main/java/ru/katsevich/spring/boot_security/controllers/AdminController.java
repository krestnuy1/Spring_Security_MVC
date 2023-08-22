package ru.katsevich.spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.katsevich.spring.boot_security.entities.Role;
import ru.katsevich.spring.boot_security.entities.User;
import ru.katsevich.spring.boot_security.services.RoleService;
import ru.katsevich.spring.boot_security.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping("add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "addOrUpdateUser";
    }


    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userID") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping()
    public String saveUserByAdmin(@ModelAttribute("user") User user,
                                  BindingResult result,
                                  @RequestParam("selectedRoles") List<Long> selectedRoleIds,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAll());
            return "addOrUpdateUser";
        }
        Set<Role> selectedRoles = new HashSet<>();
        for (Long roleId : selectedRoleIds) {
            Role role = roleService.findById(roleId).orElse(null);
            if (role != null) {
                selectedRoles.add(role);
            }
        }

        user.setRoles(selectedRoles);
        userService.save(user);

        return "redirect:/admin";
    }



    @GetMapping("/updateUser")
    public String updateUser(Model model, @RequestParam("userID") Long id) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return "addOrUpdateUser";

    }

}

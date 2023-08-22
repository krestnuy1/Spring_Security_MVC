package ru.katsevich.spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.katsevich.spring.boot_security.entities.Role;
import ru.katsevich.spring.boot_security.entities.User;
import ru.katsevich.spring.boot_security.repository.RoleRepository;
import ru.katsevich.spring.boot_security.repository.UserRepository;
import ru.katsevich.spring.boot_security.services.RoleService;
import ru.katsevich.spring.boot_security.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/user")
public class UserController {

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

    @GetMapping("/updateUser")
    public String updateUser(Model model, @RequestParam("userID") Long id) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", user.getRoles());
        return "UpdateUser";

    }

    @PostMapping()
    public String saveUserByUser(@ModelAttribute("user") User user,
                                 BindingResult result,
                                 @RequestParam("selectedRoleIds") List<Long> selectedRoleIds,
                                 Model model) {
        User user1 = userService.getById(user.getId());
        Set<Role> currentRoles = user1.getRoles();
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

        user.setRoles(currentRoles);
        userService.save(user);

        return "redirect:/user";
    }

}

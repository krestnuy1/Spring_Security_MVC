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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    private RoleRepository roleRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping("/updateUser")
    public String updateUser(Model model, @RequestParam("userID") Long id) {
        User user = userRepository.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", user.getRoles());
        return "UpdateUser";

    }

    @PostMapping()
    public String saveUserByUser(@ModelAttribute("user") User user,
                                 BindingResult result,
                                 @RequestParam("selectedRoleIds") List<Long> selectedRoleIds,
                                 Model model) {
        User user1 = userRepository.getById(user.getId());
        Set<Role> currentRoles = user1.getRoles();
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

        user.setRoles(currentRoles);
        userRepository.save(user);

        return "redirect:/user";
    }

}

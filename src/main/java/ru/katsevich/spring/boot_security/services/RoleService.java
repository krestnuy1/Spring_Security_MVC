package ru.katsevich.spring.boot_security.services;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.katsevich.spring.boot_security.entities.Role;

public interface RoleService extends JpaRepository<Role, Long> {
}

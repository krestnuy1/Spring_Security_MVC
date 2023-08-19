package ru.katsevich.spring.boot_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.katsevich.spring.boot_security.entities.Role;

import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    List<Role> findRoleByUsersId(Long roleId);

}


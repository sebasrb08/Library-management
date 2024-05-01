package com.gestion_biblioteca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion_biblioteca.entities.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findRoleyByRoleEnumIn(List<String> roleName);
}

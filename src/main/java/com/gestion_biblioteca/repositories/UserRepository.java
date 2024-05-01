package com.gestion_biblioteca.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion_biblioteca.entities.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users>findUsersByUsername(String username);
}

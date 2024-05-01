package com.gestion_biblioteca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion_biblioteca.entities.Loans;

@Repository
public interface LoanRepository extends JpaRepository<Loans,Long> {
    List<Loans> findByStateTrue();
}

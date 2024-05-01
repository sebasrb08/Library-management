package com.gestion_biblioteca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion_biblioteca.entities.Books;

@Repository
public interface BookRepository extends JpaRepository<Books,Long>{
    List<Books> findByQuantityGreaterThan(int quantity);


}

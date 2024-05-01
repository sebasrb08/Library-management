package com.gestion_biblioteca.dto;

import java.time.LocalDate;

import lombok.Data;


@Data
public class LoanDto {
    
    private long id;

    private long userId;

    private long bookId;
        
    private LocalDate returnDate;
}

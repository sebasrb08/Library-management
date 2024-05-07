package com.gestion_biblioteca.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LoanDto {
    
    private long id;

    private long userId;

    private long bookId;
        
    private LocalDate returnDate;
}

package com.gestion_biblioteca.mapper;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion_biblioteca.dto.LoanDto;
import com.gestion_biblioteca.entities.Books;
import com.gestion_biblioteca.entities.Loans;
import com.gestion_biblioteca.entities.Users;

@Component
public class MapperLoan {
    @Autowired
    private ModelMapper modelMapper;


    public LoanDto toDto(Loans loan){
        LoanDto loanDto =  modelMapper.map(loan, LoanDto.class);
        loanDto.setUserId(loan.getUser().getId());
        loanDto.setBookId(loan.getBook().getId());
        return loanDto;
    }

    public Loans toEntity(LoanDto loanDto,Users users,Books books){
        Loans loans = Loans.builder()
        .user(users)
        .book(books)
        .loanDate(LocalDate.now())
        .returnDate(loanDto.getReturnDate())
        .state(true)
        .build();
        return loans;
    }

}

package com.gestion_biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestion_biblioteca.dto.LoanDto;
import com.gestion_biblioteca.entities.Loans;

@Service
public interface LoanService {
    public List< LoanDto> getAllLoan();

    public Optional<LoanDto> getLoanById(long id);

    public Loans saveLoan(LoanDto loanDto) ;

    public Loans updateLoan(LoanDto loanDto,long id);

    public String deleteLoan(long id);

    public List<LoanDto> getAllLoanState();
}

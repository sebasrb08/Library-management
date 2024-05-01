package com.gestion_biblioteca.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gestion_biblioteca.dto.BookDto;
import com.gestion_biblioteca.dto.LoanDto;
import com.gestion_biblioteca.entities.Books;
import com.gestion_biblioteca.entities.Loans;
import com.gestion_biblioteca.entities.Users;
import com.gestion_biblioteca.mapper.MapperLoan;
import com.gestion_biblioteca.repositories.LoanRepository;
import com.gestion_biblioteca.services.BookService;
import com.gestion_biblioteca.services.LoanService;
import com.gestion_biblioteca.services.UserService;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired 
    private MapperLoan mapperLoan;

    @Override
    public List<LoanDto> getAllLoan() {

       List<Loans> loans = loanRepository.findAll();
       List<LoanDto> loanDtos =new ArrayList<>();
       for (Loans loans2 : loans) {
            loanDtos.add( mapperLoan.toDto(loans2));
       }
       return loanDtos;

    }

    @Override
    public Optional<LoanDto> getLoanById(long id) {
        Optional<Loans>  loanId = loanRepository.findById(id);
        return  loanId.map(loan -> mapperLoan.toDto(loan));
    }

    @Override
    public Loans saveLoan(LoanDto loanDto){
        Optional<Users> user = userService.getUserById2(loanDto.getUserId());
        Optional<Books> book = bookService.getBookById2(loanDto.getBookId());
        if (!user.isPresent() && !book.isPresent()) {
            return null;
        }
        if (book.get().getQuantity() < 0) {
            return null;
        }

        Loans loans = mapperLoan.toEntity(loanDto,user.get() ,book.get());
        
        
        Loans guardado = loanRepository.save(loans);
        substractQuantity(guardado);
        return loans;
    }

    @Override
    public Loans updateLoan(LoanDto loanDto, long id) {
        Optional<Loans>  loanId = loanRepository.findById(id);

        if (!loanId.isPresent()) {
            return null;
        }
        Loans existingLoan = loanId.get();

        existingLoan.getUser().setId(loanDto.getUserId());
        existingLoan.getBook().setId(loanDto.getBookId());
        existingLoan.setReturnDate(loanDto.getReturnDate());
        ;

        return loanRepository.save(existingLoan);
    }

    @Override
    public String deleteLoan(long id) {
        Optional<Loans>  loanId = loanRepository.findById(id);
        if (!loanId.isPresent()) {
            return  null;
        }
        loanRepository.delete(loanId.get());
        return "Book eliminated";
    }

    @Override
    public List<LoanDto> getAllLoanState() {
        List<Loans> loans = loanRepository.findByStateTrue();
       List<LoanDto> loanDtos =new ArrayList<>();
       for (Loans loans2 : loans) {
            loanDtos.add( mapperLoan.toDto(loans2));
       }
       return loanDtos;
    }
     @Scheduled(cron = "0 0 0 * * *")
    public void updateState(){
        List<Loans> loans = loanRepository.findAll();
       LocalDate fechaHoy=LocalDate.now();
        for (Loans loans2 : loans) {
            if (fechaHoy.isAfter( loans2.getReturnDate())) {
                loans2.setState(false);
                additionQuantity(loans2);
            }else{
                loans2.setState(true);
            }
       }
    }

    public void substractQuantity(Loans loan){
        Optional<BookDto> bookId = bookService.getBookById(loan.getBook().getId());
        BookDto bookDto = bookId.get();
        int cantidadBook = bookDto.getQuantity();
        bookDto.setQuantity(cantidadBook-1);
        
        bookService.updateBook(bookDto,loan.getBook().getId());
    }

    public void additionQuantity(Loans loan){
        Optional<BookDto> bookId = bookService.getBookById(loan.getBook().getId());
        BookDto bookDto = bookId.get();
        int cantidadBook = bookDto.getQuantity();
        bookDto.setQuantity(cantidadBook+1);
        
        bookService.updateBook(bookDto,loan.getBook().getId());
    }

}

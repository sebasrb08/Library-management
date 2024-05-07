package com.gestion_biblioteca.services.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.gestion_biblioteca.dto.BookDto;
import com.gestion_biblioteca.dto.LoanDto;
import com.gestion_biblioteca.dto.UserDto;
import com.gestion_biblioteca.entities.Books;
import com.gestion_biblioteca.entities.Loans;
import com.gestion_biblioteca.entities.Role;
import com.gestion_biblioteca.entities.RoleEnum;
import com.gestion_biblioteca.entities.Users;
import com.gestion_biblioteca.mapper.MapperLoan;
import com.gestion_biblioteca.repositories.BookRepository;
import com.gestion_biblioteca.repositories.LoanRepository;
import com.gestion_biblioteca.repositories.UserRepository;
import com.gestion_biblioteca.services.BookService;
import com.gestion_biblioteca.services.UserService;


@ExtendWith(MockitoExtension.class)
public class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private MapperLoan mapperLoan;
    
    @Spy
    @InjectMocks
    private LoanServiceImpl loanServiceImpl;

    @Mock
    private UserServiceImpl userServiceImpl;

    @Mock
    private BookServiceImpl bookServiceImpl;

    Loans loans;

    LoanDto loanDto;

    Books books;

    Users users;
    
    UserDto userDto;

    BookDto bookDto;

    @BeforeEach
    void setUp(){

        readBooks();

        readUsers();


        loans = Loans.builder()
        .id(1)
        .user(users)
        .book(books)
        .loanDate(LocalDate.now())
        .returnDate(LocalDate.of(2024, 05, 05))
        .state(true)
        .build();

        loanDto = LoanDto.builder()
        .id(1)
        .userId(1)
        .bookId(1)
        .returnDate(LocalDate.of(2024, 05, 05))
        .build();

        //Mockito.when(countryRepositoryMock.findCountryByIsoCode("DO")).thenReturn(mockCountry);

    }

    public void readBooks(){
        books = Books.builder()
        .id(1)
        .title("Harry Potter")
        .author("J.K")
        .genre("Ficcion")
        .publicationDate(LocalDate.of(1997,06,26))
        .quantity(3)
        .build();

        bookDto = new BookDto();
        bookDto.setTitle("Harry Potter");
        bookDto.setAuthor("J.K");
        bookDto.setGenre("Ficcion");
        bookDto.setPublicationDate(LocalDate.of(1997,06,26));
        bookDto.setQuantity(3);
    }

    public void readUsers(){
        Set<Role> role = new HashSet<>();
        role.add(Role.builder().id(1).roleEnum(RoleEnum.ADMIN).build());
        users = Users.builder()
        .id(1)
        .username("sebas")
        .password("123456")
        .isEnabled(true)
        .accountNoExpired(true)
        .accountNoLocked(true)
        .credentialNoExpired(true)
        .role(role)        
        .build();

        userDto = UserDto.builder()
        .id(1)
        .username("sebas")
        .roleId(List.of(1L))
        .build();

    }

    @Test
    void testDeleteLoan() {

        System.out.println(loans);
        Mockito.when(loanRepository.findById(1L)).thenReturn(Optional.of(loans));

        String message=loanServiceImpl.deleteLoan(1L);

        if (message == null) {
            Assertions.assertNull(message);
        }
        Assertions.assertNotNull(message);
    }

    @Test
    void testGetAllLoan() {

        List<Loans> listLoan = new  ArrayList<>();
        listLoan.add(loans);
        Mockito.when(loanRepository.findAll()).thenReturn(listLoan);
        when(mapperLoan.toDto(loans)).thenReturn(loanDto);

        List<LoanDto> loanDtos=loanServiceImpl.getAllLoan();

        Assertions.assertEquals(1,loanDtos.get(0).getUserId());
        Assertions.assertEquals(1,loanDtos.get(0).getBookId());
        Assertions.assertEquals(LocalDate.of(2024, 05, 05),loanDtos.get(0).getReturnDate());
        
    }


    @Test
    void testGetAllLoanState() {

        List<Loans> listLoan = new  ArrayList<>();
        listLoan.add(loans);
        Mockito.when(loanRepository.findByStateTrue()).thenReturn(listLoan);
        when(mapperLoan.toDto(loans)).thenReturn(loanDto);

        List<LoanDto> loanDtos=loanServiceImpl.getAllLoanState();

        Assertions.assertNotNull(loanDtos);
        Assertions.assertEquals(1,loanDtos.get(0).getUserId());
        Assertions.assertEquals(1,loanDtos.get(0).getBookId());
        Assertions.assertEquals(LocalDate.of(2024, 05, 05),loanDtos.get(0).getReturnDate());
    }

    @Test
    void testGetLoanById() {
        
        Mockito.when(loanRepository.findById(1L)).thenReturn(Optional.of(loans));
        when(mapperLoan.toDto(loans)).thenReturn(loanDto);

        Optional<LoanDto> loanDto2=loanServiceImpl.getLoanById(1L);

        Assertions.assertEquals(1,loanDto2.get().getUserId());
        Assertions.assertEquals(1,loanDto2.get().getBookId());
        Assertions.assertEquals(LocalDate.of(2024, 05, 05),loanDto2.get().getReturnDate());
    }

    @Test
    void testSaveLoan() {   
        Mockito.when(loanRepository.save(loans)).thenReturn(loans);
        Mockito.when(userServiceImpl.getUserById2(1L)).thenReturn(Optional.of(users));
        Mockito.when(bookServiceImpl.getBookById2(1L)).thenReturn(Optional.of(books));

        when(mapperLoan.toEntity(loanDto, users, books)).thenReturn(loans);

        
        Loans loan2=loanServiceImpl.saveLoan(loanDto);

        Assertions.assertEquals(1,loan2.getUser().getId());
        Assertions.assertEquals(1,loan2.getBook().getId());
        Assertions.assertEquals(LocalDate.now(),loan2.getLoanDate());
        Assertions.assertEquals(LocalDate.of(2024, 05, 05),loan2.getReturnDate());

        verify(loanServiceImpl, times(1)).substractQuantity(loan2);

    }

    @Test
    void testUpdateLoan() {
        Mockito.when(loanRepository.findById(1L)).thenReturn(Optional.of(loans));
        Mockito.when(loanRepository.save(loans)).thenReturn(loans);

        
        Loans loan2=loanServiceImpl.updateLoan(loanDto,1L);

        if (loan2 == null) {
            Assertions.assertNull(loan2);
        }

        Assertions.assertEquals(1,loan2.getUser().getId());
        Assertions.assertEquals(1,loan2.getBook().getId());
        Assertions.assertEquals(LocalDate.now(),loan2.getLoanDate());
        Assertions.assertEquals(LocalDate.of(2024, 05, 05),loan2.getReturnDate());
    }

}

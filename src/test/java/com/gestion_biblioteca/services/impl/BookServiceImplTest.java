package com.gestion_biblioteca.services.impl;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gestion_biblioteca.dto.BookDto;
import com.gestion_biblioteca.entities.Books;
import com.gestion_biblioteca.mapper.MapperBook;
import com.gestion_biblioteca.repositories.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepositoryMock;

    @Mock
    private MapperBook mapperBook;

    @InjectMocks
    private BookServiceImpl bookService;

    Books books;

    BookDto bookDto;

    @BeforeEach
    void setUp(){

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

        //Mockito.when(countryRepositoryMock.findCountryByIsoCode("DO")).thenReturn(mockCountry);

    }

    @Test
    void testDeleteBook() {
        Mockito.when(bookRepositoryMock.findById(1L)).thenReturn(Optional.of(books));    
        String message=bookService.deleteBook(1L);

        if (message == null) {
            Assertions.assertNull(message);
        }
        Assertions.assertNotNull(message);
        Assertions.assertEquals("Book eliminated",message);
    }

    @Test
    void testGetAllBook() {
        List<Books> listBook = new  ArrayList<>();
        listBook.add(books);
        Mockito.when(bookRepositoryMock.findAll()).thenReturn(listBook);
        when(mapperBook.toDto(books)).thenReturn(bookDto);

        List<BookDto> bookDtos=bookService.getAllBook();

        Assertions.assertEquals("Harry Potter",bookDtos.get(0).getTitle());
        Assertions.assertEquals("J.K",bookDtos.get(0).getAuthor());
        Assertions.assertEquals("Ficcion",bookDtos.get(0).getGenre());
        Assertions.assertEquals(LocalDate.of(1997,06,26),bookDtos.get(0).getPublicationDate());
        Assertions.assertEquals(3,bookDtos.get(0).getQuantity());
        
    }

    @Test
    void testGetAllBooksAvailable() {
        List<Books> listBook = new  ArrayList<>();
        listBook.add(books);
        Mockito.when(bookRepositoryMock.findByQuantityGreaterThan(0)).thenReturn(listBook);
        when(mapperBook.toDto(books)).thenReturn(bookDto);

        List<BookDto> bookDtos=bookService.getAllBooksAvailable();

        if (bookDtos.isEmpty()) {
            Assertions.assertNull(bookDtos);
        }
        Assertions.assertNotNull(bookDtos);

    }

    @Test
    void testGetBookById() {

        Mockito.when(bookRepositoryMock.findById(1L)).thenReturn(Optional.of(books));
                when(mapperBook.toDto(books)).thenReturn(bookDto);
        Optional<BookDto> bookDto=bookService.getBookById(1L);

        Assertions.assertEquals("Harry Potter",bookDto.get().getTitle());
    }

    @Test
    void testGetBookById2() {

    }

    @Test
    void testSaveBook() {
        Mockito.when(bookRepositoryMock.save(books)).thenReturn(books);
        when(mapperBook.toEntity(bookDto)).thenReturn(books);
        Books book=bookService.saveBook(bookDto);

        Assertions.assertEquals("Harry Potter",book.getTitle());
        Assertions.assertEquals("J.K",book.getAuthor());
        Assertions.assertEquals("Ficcion",book.getGenre());
        Assertions.assertEquals(LocalDate.of(1997,06,26),book.getPublicationDate());
        Assertions.assertEquals(3,book.getQuantity());

    }

    @Test
    void testUpdateBook() {
        Mockito.when(bookRepositoryMock.findById(1L)).thenReturn(Optional.of(books));
        Mockito.when(bookRepositoryMock.save(books)).thenReturn(books);
        Books book=bookService.updateBook(bookDto,1L);
        if (book == null) {
            Assertions.assertNull(book);
        }

        Assertions.assertEquals("Harry Potter",book.getTitle());
        Assertions.assertEquals("J.K",book.getAuthor());
        Assertions.assertEquals("Ficcion",book.getGenre());
        Assertions.assertEquals(LocalDate.of(1997,06,26),book.getPublicationDate());
        Assertions.assertEquals(3,book.getQuantity());
    }
}

package com.gestion_biblioteca.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion_biblioteca.dto.BookDto;
import com.gestion_biblioteca.entities.Books;
import com.gestion_biblioteca.mapper.MapperBook;
import com.gestion_biblioteca.repositories.BookRepository;
import com.gestion_biblioteca.services.BookService;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MapperBook mapperBook;

    @Override
    public List<BookDto> getAllBook() {

        List <Books>  books = bookRepository.findAll();
        return books.stream()
                    .map(book -> mapperBook.toDto(book))
                    .collect(Collectors.toList());
    }

    @Override
    public Optional<BookDto> getBookById(long id) {
        Optional<Books>  bookId = bookRepository.findById(id);
        if (!bookId.isPresent() ) {
            return null;
        }
        return  bookId.map(book -> mapperBook.toDto(book));
    }

    @Override
    public Books saveBook(BookDto bookdDto) {
        Books book = mapperBook.toEntity(bookdDto);
       return bookRepository.save(book);
     }

    @Override
    public Books updateBook(BookDto bookdDto,long id) {
        Optional<Books>  bookId = bookRepository.findById(id);

        if (!bookId.isPresent()) {
            return null;
        }
        Books existingBook = bookId.get();

        existingBook.setTitle(bookdDto.getTitle());
        existingBook.setAuthor(bookdDto.getAuthor());
        existingBook.setGenre(bookdDto.getGenre());
        existingBook.setPublicationDate(bookdDto.getPublicationDate());
        existingBook.setQuantity(bookdDto.getQuantity());
        ;

        return bookRepository.save(existingBook);

    }

    @Override
    public String deleteBook(long id) {
        Optional<Books>  bookId = bookRepository.findById(id);
        if (!bookId.isPresent()) {
            return  null;
        }
        bookRepository.delete(bookId.get());
        return "Book eliminated";
    }

    @Override
    public List<BookDto> getAllBooksAvailable() {
        List<Books> books = bookRepository.findByQuantityGreaterThan(0);
        if (books.isEmpty()) {
            return null;
        }
        return books.stream()
            .map(book-> mapperBook.toDto(book))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Books> getBookById2(long id) {
        return bookRepository.findById(id);
    }
    
}

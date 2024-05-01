package com.gestion_biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestion_biblioteca.dto.BookDto;
import com.gestion_biblioteca.entities.Books;

@Service
public interface BookService {
    public List< BookDto> getAllBook();

    public Optional<BookDto> getBookById(long id);

    public Optional<Books> getBookById2(long id);

    public Books saveBook(BookDto bookdDto);

    public Books updateBook(BookDto bookdDto,long id);

    public String deleteBook(long id);

    public List< BookDto>getAllBooksAvailable();
}

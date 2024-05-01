package com.gestion_biblioteca.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion_biblioteca.dto.BookDto;
import com.gestion_biblioteca.entities.Books;

@Component
public class MapperBook {
    @Autowired
    private ModelMapper modelMapper;


    public BookDto toDto(Books book){
        return modelMapper.map(book, BookDto.class);
    }

    public Books toEntity(BookDto bookDto){
        return modelMapper.map(bookDto, Books.class);
    }
}

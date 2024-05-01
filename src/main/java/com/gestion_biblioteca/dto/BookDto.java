package com.gestion_biblioteca.dto;

import java.time.LocalDate;

import lombok.Data;


@Data
public class BookDto {
    private long id;

    private String title;

    private String author;

    private String genre;

    private LocalDate publicationDate;

    private int quantity;
}

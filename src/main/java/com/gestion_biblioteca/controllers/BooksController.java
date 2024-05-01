package com.gestion_biblioteca.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion_biblioteca.dto.BookDto;
import com.gestion_biblioteca.entities.Books;
import com.gestion_biblioteca.services.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/book")
public class BooksController {


    @Autowired
    private BookService bookServiceImpl;

    @Operation(summary = "Retorna todos los libros",
    description = "Retorna una lista de libros.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "La lista de libros fue encontrada exitosamente.",
          content = @Content)})


    @GetMapping("/get")
    public ResponseEntity<List<BookDto>>getBooks(){
        return ResponseEntity.ok(bookServiceImpl.getAllBook());
    }

    @Operation(summary = "Retorna un libro por su ID",
    description = "Retorna un libro según el ID proporcionado.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "El libro fue encontrado.",
          content = @Content),
    @ApiResponse(responseCode = "404", description = "El libro no fue encontrado.")})

    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<BookDto>> getBookById(@PathVariable long id){
        return ResponseEntity.ok(bookServiceImpl.getBookById(id));
    }

    @Operation(summary = "Crea un libro",
    description = "Crea un libro en el sistema.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "El libro fue creado.",
          content = @Content)})

    @PostMapping("/create")
    public ResponseEntity<Books>createBook(@RequestBody BookDto bookDto){
 
        return ResponseEntity.status(HttpStatus.CREATED).body(bookServiceImpl.saveBook(bookDto));
    }

    @Operation(summary = "Actualiza un libro por su ID",
    description = "Actualiza un libro según el ID proporcionado.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "El libro fue Actualizado.",
          content = @Content),
    @ApiResponse(responseCode = "404", description = "El libro no fue Actualizado.")})

    @PutMapping("/update/{id}")
    public ResponseEntity<Books>updateBook(@RequestBody BookDto bookDto, @PathVariable Long id ){
        Books book =bookServiceImpl.updateBook(bookDto,id);
        if (book== null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @Operation(summary = "Elimina un libro por su ID",
    description = "Elimina un libro según el ID proporcionado.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "El libro fue eliminado.",
          content = @Content),
    @ApiResponse(responseCode = "404", description = "El libro no fue eliminado.")})

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String>DeleteBook( @PathVariable Long id){
        String message = bookServiceImpl.deleteBook(id);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Retorna todos los libros disponibles ",
    description = "Retorna una lista de libros disponibles,los cuales la cantidad sea mayor a 0.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "El libro fue encontrado.",
          content = @Content)})

    @GetMapping("/get/available")
    public ResponseEntity<List<BookDto>>getBookAvailable(){
        return ResponseEntity.ok(bookServiceImpl.getAllBooksAvailable());
    }

}

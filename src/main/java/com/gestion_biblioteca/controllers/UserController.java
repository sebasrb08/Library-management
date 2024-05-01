package com.gestion_biblioteca.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion_biblioteca.dto.UserDto;
import com.gestion_biblioteca.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;


    @Operation(summary = "Retorna todos los usuario ",
    description = "Retorna una lista de usuarios.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "la lista de usuarios fue encontrada exitosamente.",
          content = @Content)})

    @GetMapping("/get")
    private ResponseEntity<List<UserDto>> getAllUser(){
        return ResponseEntity.ok( userService.getAllUser());
    }

    @Operation(summary = "Retorna un usuario por su ID",
    description = "Retorna un usuario según el ID proporcionado.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "El usuario fue encontrado.",
          content = @Content),
    @ApiResponse(responseCode = "404", description = "El usuario no fue encontrado.")})

    @GetMapping("/get/{id}")
    private ResponseEntity<Optional<UserDto>> getAllUser(@PathVariable long id){
        return ResponseEntity.ok( userService.getUserById(id));
    }
    
    @Operation(summary = "Actualiza un usuario por su ID",
    description = "Actualiza un usuario según el ID proporcionado.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "El usuario fue Eliminado.",
          content = @Content),
    @ApiResponse(responseCode = "404", description = "El usuario no fue Eliminado.")})

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<String> deleteUser(@PathVariable long id){

         String message = userService.deleteUserDto(id);

        if (message == null ) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message);
    }
    

}

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

import com.gestion_biblioteca.dto.LoanDto;
import com.gestion_biblioteca.entities.Loans;
import com.gestion_biblioteca.services.LoanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/loan")
public class LoansController {


    @Autowired
    private LoanService loanService;

    @Operation(summary = "Obtener un préstamo por su ID",
               description = "Retorna un préstamo según el ID proporcionado.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "El préstamo fue encontrado.",
                     content = @Content),
    @ApiResponse(responseCode = "404", description = "El préstamo no fue encontrado.")})

    @GetMapping("/approval/{id}")
    public ResponseEntity<Optional<LoanDto>> approvalLoan(@PathVariable long id){
        Optional<LoanDto> loan =loanService.getLoanById(id);
        if (loan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loan);
    }

    @Operation(summary = "Crea un préstamo ",
    description = "Crea un prestamo en el sistema.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "El préstamo fue creado.",
          content = @Content),})

    @PostMapping("/request")
    public ResponseEntity<Loans> requestLoan(@RequestBody LoanDto loanDto ){
        Loans loan = loanService.saveLoan(loanDto);
        if (loan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(loan);
    }

    @Operation(summary = "Actualiza un préstamo por su ID",
    description = "Actualiza un préstamo según el ID proporcionado.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "El préstamo fue modificado.",
          content = @Content),
    @ApiResponse(responseCode = "404", description = "El préstamo no fue modificado.")})

    @PutMapping("/return/{id}")
    public ResponseEntity<Loans> returnLoan(@RequestBody LoanDto loanDto,@PathVariable Long id){
        Loans loan =loanService.updateLoan(loanDto, id);
        if (loan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loan);

    }

    @Operation(summary = "Elimina un préstamo por su ID",
    description = "Elimina un préstamo según el ID proporcionado.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "El préstamo fue eliminado.",
          content = @Content),
    @ApiResponse(responseCode = "404", description = "El préstamo no fue eliminado.")})

    @DeleteMapping("/cancellation/{id}")
    public ResponseEntity<String> cancellationLoan(@PathVariable long id){
        String message =loanService.deleteLoan(id);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Retorna todos los préstamos activos",
    description = "Retorna una lista de todos los préstamos activos")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "La lista de préstamos activos fue obtenida exitosamente.",
          content = @Content)})

    @GetMapping("/state")
    public ResponseEntity<List<LoanDto>> stateLoan(){
        return ResponseEntity.ok(loanService.getAllLoanState());
    }

}

package com.gestion_biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion_biblioteca.dto.AuthCreateUserRequest;
import com.gestion_biblioteca.dto.AuthLoginRequest;
import com.gestion_biblioteca.dto.AuthResponse;
import com.gestion_biblioteca.services.impl.JwtUserDetailServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private JwtUserDetailServiceImpl userDetailsService;


    @Operation(summary = "Añade un usuario",
    description = "Añade un nuevo usuario en el sistema .")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "El usuario fue creado exitosamente.",
          content = @Content)})

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUser){
       
        return new ResponseEntity<>(this.userDetailsService.createUser(authCreateUser),HttpStatus.CREATED);

    }

    @Operation(summary = "Inicio de sesión",
    description = "Inicia sesion a el usuario, y retorna el token para poder acceder a los demas endpoints segun sea su rol .")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "El usuario fue autenticado correctamente y se generó el token.",
          content = @Content)})

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse>login(@RequestBody @Valid AuthLoginRequest userRequest){
        
        return new ResponseEntity<>(this.userDetailsService.loginUser(userRequest),HttpStatus.OK);
    }
}

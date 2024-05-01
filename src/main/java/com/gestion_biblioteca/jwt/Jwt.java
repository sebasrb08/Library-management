package com.gestion_biblioteca.jwt;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class Jwt {
    
    @Value("${security.jwt.key.private}")
    private String privateKey; 

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    public String createToken(Authentication authentication){
        //Definimos el algoritmo de encriptacion
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        // extraemos el usuario autenticado
        String username = authentication.getPrincipal().toString();
        // Va a obtener todas las autorizaciones,con el metodo map los devuelve con un string y con el metodo Collectors.joining va a tomar cada uno de los permisos y depararlos por coma
        String authority= authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

        //Creamos el token
        String jwtToken = JWT.create()
        .withIssuer(this.userGenerator)//el usuario que va a generar el token,es decir el backend
        
        .withSubject(username)//el sujeto  a quien se le va a generar el tokeb,en ese caso al usuario que se esta autenticando
        
        .withClaim("authority", authority)//va a mostrar los claims(PAYLOAD) de los permisos que tiene el usuario
        
        .withIssuedAt(new Date()) //fecha en la que se va a genera el token

        .withExpiresAt(new Date(System.currentTimeMillis()+1800000)) // expiración del token y se convierte a milisegundos
        
        .withJWTId(UUID.randomUUID().toString())//genera un id aleatorio para el token
        
        .withNotBefore(new Date(System.currentTimeMillis())) //hace que el token sea valido desde el momento que lo crean
        
        .sign(algorithm); //se genera la firma

        //retorname el token
        return jwtToken;
    }

    public DecodedJWT validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(this.userGenerator)
                .build();
                
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token invalid, not authorized");
        }
    }

    public String extractUsername(DecodedJWT decodedJWT){
        //obtenemos el usuario que viene dentro del token
        return decodedJWT.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    public Map<String,Claim> returnAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }

}

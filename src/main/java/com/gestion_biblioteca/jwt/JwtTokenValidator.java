package com.gestion_biblioteca.jwt;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

public class JwtTokenValidator extends OncePerRequestFilter {

    private Jwt jwt;

    public JwtTokenValidator(Jwt jwt) {
        this.jwt = jwt;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain)
                                    throws ServletException, IOException {
            //obtiene el header de la peticion
            String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            
            if (jwtToken != null) {

                //extrae el String desde el index 7
                jwtToken = jwtToken.substring(7);

                //validamos el token
                DecodedJWT decodedJWT = jwt.validateToken(jwtToken);

                //extraemos el usario y enviamos el token decodificado
                String username= jwt.extractUsername(decodedJWT);
                //recuperamos los permisos que tiene el usuario
                String StringAuthorities = jwt.getSpecificClaim(decodedJWT, "authority").asString();

                // seteamos el usuario

                // los permisos separados por coma los convierte  en una lista
                Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(StringAuthorities);
                
                //extraemos el contexto de spring security
                SecurityContext context = SecurityContextHolder.getContext();
                //estoy declarando el objeto authentication
                Authentication authentication = new UsernamePasswordAuthenticationToken( username,  null,authorities);

                //enviamos la autenticacion del usuario
                context.setAuthentication(authentication);

                //le seteamos el contexto
                SecurityContextHolder.setContext(context);
            }

            filterChain.doFilter(request, response);

    }
    
}

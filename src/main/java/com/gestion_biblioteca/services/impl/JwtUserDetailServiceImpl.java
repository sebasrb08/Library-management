package com.gestion_biblioteca.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gestion_biblioteca.dto.AuthCreateUserRequest;
import com.gestion_biblioteca.dto.AuthLoginRequest;
import com.gestion_biblioteca.dto.AuthResponse;
import com.gestion_biblioteca.entities.Role;
import com.gestion_biblioteca.entities.Users;
import com.gestion_biblioteca.jwt.Jwt;
import com.gestion_biblioteca.repositories.RoleRepository;
import com.gestion_biblioteca.repositories.UserRepository;

import jakarta.validation.Valid;

@Service
public class JwtUserDetailServiceImpl  implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Jwt jwt;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        Users user = userRepository.findUsersByUsername(username)
        .orElseThrow(()-> new UsernameNotFoundException("the user "+username+" doesn't exist"));

        List <SimpleGrantedAuthority> authorityList =new ArrayList<>();
        
        user.getRole()
        .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name() ))));


        return new User(
            user.getUsername(),
            user.getPassword(),
            user.isEnabled(),
            user.isAccountNoExpired(),
            user.isCredentialNoExpired(),
            user.isAccountNoLocked(),
            authorityList
        );
    }

    public Authentication authenticated( String username, String password){
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username o password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword()) ) {
            throw new BadCredentialsException("Invalid password");

        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(),userDetails.getAuthorities());

    }



    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest){
            
            String username = authCreateUserRequest.username();
            String password = authCreateUserRequest.password();

             List<String> roleRequest=authCreateUserRequest.authCreateRoleRequest().roleName();

             Set<Role> roleEntitySet = roleRepository.findRoleyByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());

            if (roleEntitySet.isEmpty()) {
                throw new IllegalArgumentException("The role specified not exist");
            }
            Users user = Users.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .role(roleEntitySet)
            .isEnabled(true)
            .accountNoLocked(true)
            .accountNoExpired(true)
            .credentialNoExpired(true)
            .build();

            Users userSaved= userRepository.save(user);

            ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

            userSaved.getRole().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
    

            Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);

            String accessToken = jwt.createToken(authentication);

            AuthResponse authResponse = new AuthResponse(username, "User created successfully", accessToken, true);

            return authResponse;
    }

    
    public AuthResponse loginUser(@Valid AuthLoginRequest authLoginRequest) {
        String username=authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication =this.authenticated(username,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accesToken = jwt.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(username, "user loged successfully", accesToken, true);

        return authResponse;
    }

    
}

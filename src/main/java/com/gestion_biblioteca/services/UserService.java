package com.gestion_biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestion_biblioteca.dto.UserDto;
import com.gestion_biblioteca.entities.Users;

@Service
public interface UserService {
    
    public List<UserDto>  getAllUser();

    public Optional<UserDto> getUserById(long id);

    public Optional<Users> getUserById2(long id);

    public String deleteUserDto(long id);
}

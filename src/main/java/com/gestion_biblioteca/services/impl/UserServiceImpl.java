package com.gestion_biblioteca.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion_biblioteca.dto.UserDto;
import com.gestion_biblioteca.entities.Users;
import com.gestion_biblioteca.mapper.MapperUser;
import com.gestion_biblioteca.repositories.UserRepository;
import com.gestion_biblioteca.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MapperUser mapperUser;

    @Override
    public List<UserDto> getAllUser() {
        
        List<Users> users = userRepository.findAll();
        List<UserDto> userDto = new ArrayList<>();
        for (Users users2 : users) {
             userDto.add(mapperUser.toDto(users2));
        }
        return userDto;
    }

    @Override
    public Optional<UserDto> getUserById(long id) {
        Optional<Users> user =  userRepository.findById(id);
        return user.map(userId->mapperUser.toDto(userId));
    }

    @Override
    public String deleteUserDto(long id) {
        Optional<Users>  userId = userRepository.findById(id);
        if (!userId.isPresent()) {
            return  null;
        }
        userRepository.delete(userId.get());
        return "user eliminated";
    }

    @Override
    public Optional<Users> getUserById2(long id) {
        return userRepository.findById(id);
    }
    
}

package com.gestion_biblioteca.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestion_biblioteca.dto.UserDto;
import com.gestion_biblioteca.entities.Role;
import com.gestion_biblioteca.entities.Users;

@Component
public class MapperUser {
     @Autowired
    private ModelMapper modelMapper;


    public UserDto toDto(Users user){
         UserDto userDto=modelMapper.map(user, UserDto.class);
        List<Long> roleIdList = user.getRole().stream()
                                          .map(Role::getId)
                                          .collect(Collectors.toList());
        userDto.setRoleId(roleIdList);
        return userDto;
        
    }

    public Users toEntity(UserDto userDto){
        return modelMapper.map(userDto, Users.class);
    }
}

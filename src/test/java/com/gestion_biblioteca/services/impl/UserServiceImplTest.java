package com.gestion_biblioteca.services.impl;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.swing.text.html.Option;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gestion_biblioteca.dto.UserDto;
import com.gestion_biblioteca.entities.Role;
import com.gestion_biblioteca.entities.RoleEnum;
import com.gestion_biblioteca.entities.Users;
import com.gestion_biblioteca.mapper.MapperUser;
import com.gestion_biblioteca.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private MapperUser mapperUser;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    Users users;

    UserDto userDto;

    @BeforeEach
    void setUp(){
        Set<Role> role = new HashSet<>();
        role.add(Role.builder().id(1).roleEnum(RoleEnum.ADMIN).build());
        users = Users.builder()
        .id(1)
        .username("sebas")
        .password("123456")
        .isEnabled(true)
        .accountNoExpired(true)
        .accountNoLocked(true)
        .credentialNoExpired(true)
        .role(role)        
        .build();

        userDto = UserDto.builder()
        .id(1)
        .username("sebas")
        .roleId(List.of(1L))
        .build();

        //Mockito.when(countryRepositoryMock.findCountryByIsoCode("DO")).thenReturn(mockCountry);

    }

    @Test
    void testDeleteUserDto() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(users));

        String users2=userServiceImpl.deleteUserDto(1L);
       if (users2 == null) {
            Assertions.assertNull(users2);
       }

       Assertions.assertNotNull(users2);
    }

    @Test
    void testGetAllUser() {
        List<Users> listUser = new  ArrayList<>();
        listUser.add(users);
        Mockito.when(userRepository.findAll()).thenReturn(listUser);
        when(mapperUser.toDto(users)).thenReturn(userDto);

        List<UserDto> userDtos=userServiceImpl.getAllUser();
        Assertions.assertEquals(1L,userDtos.get(0).getId());
        Assertions.assertEquals("sebas",userDtos.get(0).getUsername());
        Assertions.assertEquals(1L,userDtos.get(0).getRoleId().get(0));
        
    }

    @Test
    void testGetUserById() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(users));
        when(mapperUser.toDto(users)).thenReturn(userDto);

        Optional<UserDto> userDtos=userServiceImpl.getUserById(1L);
        Assertions.assertEquals(1L,userDtos.get().getId());
        Assertions.assertEquals("sebas",userDtos.get().getUsername());
        Assertions.assertEquals(1L,userDtos.get().getRoleId().get(0));
    }

}

package com.gestion_biblioteca.dto;



import java.util.List;

import lombok.Data;

@Data
public class UserDto {
    
    private long id;

    private String username;

    private List<Long> roleId;

}

package com.gestion_biblioteca.dto;



import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    
    private long id;

    private String username;

    private List<Long> roleId;

}

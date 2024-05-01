package com.gestion_biblioteca.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;


public record AuthCreateRoleRequest(
    @NotEmpty List<String> roleName) {
    
}

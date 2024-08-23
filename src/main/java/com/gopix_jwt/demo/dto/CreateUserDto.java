package com.gopix_jwt.demo.dto;

import com.gopix_jwt.demo.enums.RoleName;

public record CreateUserDto(

        String email,
        String password,
        RoleName role

) {
}

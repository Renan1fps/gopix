package com.gopix_jwt.demo.dto;

import com.gopix_jwt.demo.entity.Role;

import java.util.List;

public record RecoveryUserDto(

        Long id,
        String email,
        List<Role> roles

) {
}

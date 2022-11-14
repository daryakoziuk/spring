package com.dmdev.service.dto;

import com.dmdev.service.entity.Role;
import lombok.Value;

@Value
public class UserCreateDto {

    String username;
    String firstname;
    String lastname;
    String password;
    Role role;
}

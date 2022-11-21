package com.dmdev.service.dto;

import com.dmdev.service.entity.Role;
import lombok.Value;

@Value
public class UserEditDto {

    String username;
    String firstname;
    String lastname;
    Role role;
}

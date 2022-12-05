package com.dmdev.service.dto;

import com.dmdev.service.entity.Role;
import lombok.Value;

@Value
public class UserReadDto {

    Long id;
    String image;
    String username;
    String firstname;
    String lastname;
    Role role;
}

package com.dmdev.service.dto;

import com.dmdev.service.contoller.validation.EnumName;
import com.dmdev.service.entity.Role;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class UserEditDto {

    @Email(message = "Must have an email address format")
    @NotBlank(message = "Username can't be blank")
    String username;

    @NotBlank(message = "Firstname can't be blank")
    String firstname;

    @NotBlank(message = "Lastname can't be blank")
    String lastname;

    @EnumName(regexp = "(USER|ADMIN)")
    Role role;
}

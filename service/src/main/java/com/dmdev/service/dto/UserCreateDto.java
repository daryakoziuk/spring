package com.dmdev.service.dto;

import com.dmdev.service.contoller.validation.EnumName;
import com.dmdev.service.entity.Role;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
public class UserCreateDto {

    @Email
    @NotBlank
    String username;

    @NotBlank
    String firstname;

    MultipartFile image;

    @NotBlank
    String lastname;

    @NotBlank
    @Size(min = 8)
    String password;

    @EnumName(regexp = "(USER|ADMIN)")
    Role role;
}

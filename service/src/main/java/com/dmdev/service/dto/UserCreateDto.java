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

    @Email(message = "Must have an email address format")
    @NotBlank(message = "Username can't be blank")
    String username;

    @NotBlank(message = "Firstname can't be blank")
    String firstname;

    MultipartFile image;

    @NotBlank(message = "Lastname can't be blank")
    String lastname;

    @NotBlank(message = "Password can't be blank")
    @Size(min = 8, message = "Password must contains at least 8 characters")
    String password;

    @EnumName(regexp = "(USER|ADMIN)")
    Role role;
}

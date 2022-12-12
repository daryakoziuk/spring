package com.dmdev.service.dto;

import com.dmdev.service.contoller.validation.EnumName;
import com.dmdev.service.entity.Role;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class UserEditDto {

    @Email
    @NotBlank
    String username;

    @NotBlank
    String firstname;

    MultipartFile image;

    @NotBlank
    String lastname;

    @EnumName(regexp = "(USER|ADMIN)")
    Role role;
}

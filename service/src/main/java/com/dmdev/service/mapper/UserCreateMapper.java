package com.dmdev.service.mapper;

import com.dmdev.service.dto.UserCreateDto;
import com.dmdev.service.entity.PersonalInfo;
import com.dmdev.service.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateDto userCreateDto) {
        User user = new User();
        Optional.ofNullable(userCreateDto.getPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setLastname(userCreateDto.getLastname());
        personalInfo.setFirstname(userCreateDto.getFirstname());
        user.setPersonalInfo(personalInfo);
        user.setRole(userCreateDto.getRole());
        Optional.ofNullable(userCreateDto.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setImage(image.getOriginalFilename()));

        user.setUsername(userCreateDto.getUsername());
        return user;
    }
}

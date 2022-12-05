package com.dmdev.service.mapper;

import com.dmdev.service.dto.UserEditDto;
import com.dmdev.service.entity.PersonalInfo;
import com.dmdev.service.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class UserEditMapper implements MapperUpdate<UserEditDto, User> {

    @Override
    public User map(UserEditDto userCreateEditDto, User toObject) {
        copy(userCreateEditDto, toObject, toObject.getPersonalInfo());
        return toObject;
    }

    private void copy(UserEditDto editDto, User user, PersonalInfo personalInfo) {
        personalInfo.setFirstname(editDto.getFirstname());
        personalInfo.setLastname(editDto.getLastname());
        user.setUsername(editDto.getUsername());
        user.setPersonalInfo(personalInfo);
        user.setRole(editDto.getRole());
        Optional.ofNullable(editDto.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setImage(image.getOriginalFilename()));
    }
}

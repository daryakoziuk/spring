package com.dmdev.service.mapper;

import com.dmdev.service.dto.UserEditDto;
import com.dmdev.service.entity.PersonalInfo;
import com.dmdev.service.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEditMapper implements Mapper<UserEditDto, User> {

    @Override
    public User map(UserEditDto userCreateEditDto, User toObject) {
        copy(userCreateEditDto, toObject, toObject.getPersonalInfo());
        return toObject;
    }

    private void copy(UserEditDto userCreateEditDto, User user, PersonalInfo personalInfo) {
        personalInfo.setFirstname(userCreateEditDto.getFirstname());
        personalInfo.setLastname(userCreateEditDto.getLastname());
        user.setUsername(userCreateEditDto.getUsername());
        user.setPersonalInfo(personalInfo);
        user.setRole(userCreateEditDto.getRole());
    }
}

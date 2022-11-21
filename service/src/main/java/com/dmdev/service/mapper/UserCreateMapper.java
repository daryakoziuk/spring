package com.dmdev.service.mapper;

import com.dmdev.service.dto.UserCreateDto;
import com.dmdev.service.entity.PersonalInfo;
import com.dmdev.service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    @Override
    public User map(UserCreateDto userCreateDto) {
        User user = new User();
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setLastname(userCreateDto.getLastname());
        personalInfo.setFirstname(userCreateDto.getFirstname());
        user.setPersonalInfo(personalInfo);
        user.setPassword(userCreateDto.getPassword());
        user.setRole(userCreateDto.getRole());
        user.setUsername(userCreateDto.getUsername());
        return user;
    }
}

package com.dmdev.service.mapper;

import com.dmdev.service.dto.UserReadDto;
import com.dmdev.service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User user) {
        return new UserReadDto(
                user.getId(),
                user.getUsername(),
                user.getPersonalInfo().getFirstname(),
                user.getPersonalInfo().getLastname(),
                user.getRole());
    }
}

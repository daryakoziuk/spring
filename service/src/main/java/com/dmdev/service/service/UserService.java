package com.dmdev.service.service;

import com.dmdev.service.dao.UserRepository;
import com.dmdev.service.dto.UserCreateDto;
import com.dmdev.service.dto.UserEditDto;
import com.dmdev.service.dto.UserReadDto;
import com.dmdev.service.mapper.UserCreateMapper;
import com.dmdev.service.mapper.UserEditMapper;
import com.dmdev.service.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserEditMapper userEditMapper;
    private final UserCreateMapper userCreateMapper;

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateDto userCreateDto) {
        return Optional.of(userCreateDto)
                .map(userCreateMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserEditDto userDto) {
        return userRepository.findById(id)
                .map(user -> userEditMapper.map(userDto, user))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}

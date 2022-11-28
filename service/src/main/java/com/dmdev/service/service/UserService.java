package com.dmdev.service.service;

import com.dmdev.service.dao.UserRepository;
import com.dmdev.service.dto.UserCreateDto;
import com.dmdev.service.dto.UserEditDto;
import com.dmdev.service.dto.UserReadDto;
import com.dmdev.service.entity.User;
import com.dmdev.service.mapper.UserCreateMapper;
import com.dmdev.service.mapper.UserEditMapper;
import com.dmdev.service.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserEditMapper userEditMapper;
    private final UserCreateMapper userCreateMapper;
    private final ImageService imageService;

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
                .map(user -> {
                    uploadImage(user.getImage());
                    return userCreateMapper.map(user);
                })
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserEditDto userDto) {
        return userRepository.findById(id)
                .map(user -> {
                    if (userDto.getImage() != null) {
                        uploadImage(userDto.getImage());
                    }
                    return userEditMapper.map(userDto, user);
                })
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

    public Optional<byte[]> findImage(Long id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())))
                .orElseThrow(() -> new UsernameNotFoundException("User with such username wasn't found" + username));
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }
}

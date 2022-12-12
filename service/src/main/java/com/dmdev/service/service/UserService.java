package com.dmdev.service.service;

import com.dmdev.service.dao.UserRepository;
import com.dmdev.service.dto.UserCreateDto;
import com.dmdev.service.dto.UserEditDto;
import com.dmdev.service.dto.UserReadDto;
import com.dmdev.service.entity.User;
import com.dmdev.service.mapper.Mapper;
import com.dmdev.service.mapper.UserCreateMapper;
import com.dmdev.service.mapper.UserEditMapper;
import lombok.Getter;
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
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
@Getter
public class UserService implements UserDetailsService, CrudService<UserReadDto, User, Long> {

    private final UserRepository repository;
    private final Mapper<User, UserReadDto> mapper;
    private final UserEditMapper mapperUpdate;
    private final UserCreateMapper userCreateMapper;
    private final ImageService imageService;

    @Transactional
    public UserReadDto create(UserCreateDto userCreateDto) {
        return Optional.of(userCreateDto)
                .map(user -> {
                    uploadImage(user.getImage());
                    return userCreateMapper.map(user);
                })
                .map(repository::save)
                .map(mapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserEditDto userDto) {
        return repository.findById(id)
                .map(user -> {
                    if (userDto.getImage() != null) {
                        uploadImage(userDto.getImage());
                    }
                    return mapperUpdate.map(userDto, user);
                })
                .map(repository::saveAndFlush)
                .map(mapper::map);
    }

    public Optional<byte[]> findImage(Long id) {
        return repository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findUserByUsername(username)
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

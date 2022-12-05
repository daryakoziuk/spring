package com.dmdev.service.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@RequiredArgsConstructor
@Service
public class ImageService {

    @Value("${app.image.path}")
    private String path;

    @SneakyThrows
    public void upload(String imagePath, InputStream content) {
        Path fullPath = Path.of(path, imagePath);
        try (content) {
            Files.createDirectories(fullPath.getParent());
            Files.write(fullPath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(String imagePath) {
        Path fullPath = Path.of(path, imagePath);
        return Files.exists(fullPath)
                ? Optional.of(Files.readAllBytes(fullPath))
                : Optional.empty();
    }
}

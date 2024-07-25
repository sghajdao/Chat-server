package com.chat.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

@Service
public class ImageService {
    public byte[] getImageByName(String imageName) throws IOException {
        Path imagePath = Paths.get(System.getProperty("user.dir") + "/uploads/" + imageName);
        return Files.readAllBytes(imagePath);
    }
}

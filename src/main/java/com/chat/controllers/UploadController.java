package com.chat.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chat.dto.ImageResponse;
import com.chat.services.ImageService;

@RestController
@RequestMapping("/api/image")
public class UploadController {
    @Autowired
    private ImageService imageService;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @GetMapping("/{imageName}")
    public ResponseEntity<byte[]> displayUploadForm(@PathVariable String imageName) {
        try {
            byte[] imageBytes = imageService.getImageByName(imageName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Adjust the media type based on your image type

            return new ResponseEntity<byte[]>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageResponse> uploadImage(Model model, @RequestParam("image") MultipartFile file)
            throws IOException {
        ImageResponse response = new ImageResponse();

        String originalFilename = file.getOriginalFilename();
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, originalFilename);
        fileNames.append(originalFilename);
        Files.write(fileNameAndPath, file.getBytes());
        model.addAttribute("msg", "Uploaded image: " + fileNames.toString());
        response.setName(fileNames.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

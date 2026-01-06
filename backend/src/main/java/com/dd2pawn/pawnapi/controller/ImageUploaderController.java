package com.dd2pawn.pawnapi.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dd2pawn.pawnapi.service.ImageUpload;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ImageUploaderController {
    private final ImageUpload imageUpload;
    
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("image")MultipartFile multipartFile) throws IOException {
        String imageURL = imageUpload.uploadFile(multipartFile);
        return imageURL;
    }
}

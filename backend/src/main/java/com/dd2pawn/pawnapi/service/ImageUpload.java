package com.dd2pawn.pawnapi.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUpload {
    String uploadFile(MultipartFile file) throws IOException;
}

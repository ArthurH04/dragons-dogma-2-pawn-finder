package com.dd2pawn.pawnapi.service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageUploadImpl implements ImageUpload {
    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
                String publicId = UUID.randomUUID().toString();

        cloudinary.uploader().upload(file.getBytes(), 
            Map.of("public_id", publicId));

        String optimizedUrl = cloudinary.url().transformation(new Transformation().fetchFormat("auto").quality("auto").width(1200).crop("limit")).generate(publicId);

        return optimizedUrl;
    }
}

package com.fixcity.fixcity.media;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageUploadService {
    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) {
        String contentType = file.getContentType();
        if(contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Imagine invalida"); // trebuie sa creez un exception handler personalizat
        }
        try {
            Map<String, Object> image = ObjectUtils.asMap(
                    "folder", "fixcity/reports/images",
                    "use_filename", true,
                    "unique_filename", true,
                    "overwrite", false,
                    "resource_type", "image"
            );

            Map<?, ?> res = cloudinary.uploader().upload(file.getBytes(), image);

            return res.get("secure_url").toString();

        }catch (IOException e) {
            throw new RuntimeException("Eroare la upload", e);// trebuie sa creez un exception handler personalizat
        }
    }
}

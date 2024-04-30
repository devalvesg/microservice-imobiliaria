package com.glimoveis.Imob_back.services;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.glimoveis.Imob_back.DTOs.ImmobilesDTO;
import com.glimoveis.Imob_back.DTOs.Responses.ImmobileResponse;
import com.glimoveis.Imob_back.configs.aws.AmazonS3Config;
import com.glimoveis.Imob_back.models.Immobiles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class S3Service {

    @Value("${aws.bucketName}")
    private String bucketName;

    private AmazonS3Config amazonS3Config;

    public S3Service(AmazonS3Config amazonS3Config){
        this.amazonS3Config = amazonS3Config;
    }
    public List<String> saveImagesToBucket(List<MultipartFile> images, String id) throws IOException {
        List<String> keyImages = new ArrayList();
        for(MultipartFile file : images){
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            String key = id + "/images/" + file.getOriginalFilename();
            keyImages.add(key);
            amazonS3Config.amazonS3Client().putObject(bucketName, key, file.getInputStream(), metadata);
        }
        return keyImages;
    }

    public List<String> generateUrlImages(Immobiles imob){
        List<String> presignedUrls = new ArrayList<>();
        for (String image : imob.getImages()) {
            presignedUrls.add(amazonS3Config.amazonS3Client()
                    .generatePresignedUrl(
                            bucketName,
                            image,
                            Date.from(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))).toString());
        }

        return presignedUrls;
    }
}

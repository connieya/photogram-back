package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<Image> select(int principalId){
        List<Image> images = imageRepository.mStory(principalId);
        return images;
    }

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void upload(ImageUploadDto imageUploadDto , PrincipalDetails principalDetails) {
        // uuid 란?
        // 네트워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규약
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder+imageFileName);

        try{
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }

        Image image = imageUploadDto.toEntity(imageFileName,principalDetails.getUser());
        imageRepository.save(image);
    }
}

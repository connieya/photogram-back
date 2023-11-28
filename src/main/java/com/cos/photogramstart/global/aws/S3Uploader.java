package com.cos.photogramstart.global.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cos.photogramstart.global.common.Image;
import com.cos.photogramstart.global.util.ImageUtil;
import com.cos.photogramstart.handler.exception.FileConvertFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Uploader {

    private final AmazonS3 s3Client;


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public Image uploadImage(MultipartFile file, String dirName)  {
        Image image = ImageUtil.convertMultipartToImage(file);
        String filename = convertToFilename(dirName, image);
        File uploadFile = convertMultipartFileToFile(file);
        String url = upload(uploadFile, filename);
        image.setImageUrl(url);
        return image;
    }

    private String upload(File uploadFile, String fileName) {
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)
        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    private String putS3(File uploadFile, String fileName) {
        s3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)    // PublicRead 권한으로 업로드 됨
        );
        return s3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile)  {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file); InputStream is = multipartFile.getInputStream()) {
            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }catch (IOException e) {
            throw new FileConvertFailException("변환할 수 없는 파일입니다.");
        }
        return file;
    }

    private String convertToFilename(String dirName, Image image){
        return dirName + "/" + image.getImageUUID()+"_"+image.getImageName()+"."+image.getImageType();
    }



}

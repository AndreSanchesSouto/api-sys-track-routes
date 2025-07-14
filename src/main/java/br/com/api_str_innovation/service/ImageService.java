package br.com.api_str_innovation.service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.bucket.name}")
    private String bucketName;

    public String uploadImage(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        try {
            File file = this.convertMultipartToFile(multipartFile);
            amazonS3.putObject(bucketName, fileName, file);
            file.delete();
            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (Exception e) {
            System.out.println("ERROOOOOOOOOOOO " + e);
        }
        return null;
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File convertFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convertFile;
    }

}

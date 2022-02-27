package es.codeurjc.mca.practica_1_cloud_ordinaria_2021.image;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service("storageService")
@Profile("production")
public class S3ImageService implements ImageService {

    @Value("${amazon.s3.bucket-name}")
    private String bucketName;

    @Value("${amazon.s3.endpoint}")
    private String url;

    @Value("${amazon.s3.region}")
    private String region;

    protected S3Client s3Client;

    @PostConstruct
    public void init() {
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .build();
        System.out.println(region);
        this.createBucket(bucketName);
    }

    @Override
    public String createImage(MultipartFile multiPartFile) {
        try {
            return this.uploadFile(multiPartFile);
        } catch (IOException e) {
            // log.error("ERROR al subir la imagen al bucket.", e);
            return null;
        }
    }

    @Override
    public void deleteImage(String image) {
        try {
            this.deleteObject(image);
        } catch (Exception e) {
            // log.error("ERROR al eliminar la imagen del bucket.", e);
        }
    }

    private void createBucket(final String bucketName) {
        this.s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
    }

    public String uploadFile(MultipartFile multiPartFile) throws IllegalStateException,
            IOException {
        String fileName = multiPartFile.getOriginalFilename();
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        multiPartFile.transferTo(file);
        final PutObjectResponse putObjectResponse = this.s3Client.putObject(
                PutObjectRequest.builder().bucket(bucketName).key(fileName).build(),
                RequestBody.fromFile(file));

        return bucketName + "/" + fileName;
    }

    public void deleteObject(final String objectName) {
        this.s3Client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
    }

}

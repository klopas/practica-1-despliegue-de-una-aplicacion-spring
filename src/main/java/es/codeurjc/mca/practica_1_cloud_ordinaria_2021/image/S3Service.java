package es.codeurjc.mca.practica_1_cloud_ordinaria_2021.image;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import es.codeurjc.mca.practica_1_cloud_ordinaria_2021.image.config.S3ConfigurationProperties;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
public class S3Service {

  private final S3ConfigurationProperties properties;

  private final S3Client s3Client;

  public S3Service(final S3ConfigurationProperties properties) {
    this.properties = properties;
    this.s3Client = S3Client.builder()
      .region(Region.of(this.properties.getRegion()))
      .build();

    this.createBucket(properties.getBucketName());
  }

  private void createBucket(final String bucketName) {
    this.s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
  }

  public String uploadFile(MultipartFile multiPartFile) throws IllegalStateException,
    IOException {
    String fileName = multiPartFile.getOriginalFilename();
    File file = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
    multiPartFile.transferTo(file);
    final PutObjectResponse putObjectResponse = this.s3Client.putObject(
      PutObjectRequest.builder().bucket(this.properties.getBucketName()).key(fileName).build(),
      RequestBody.fromFile(file));

    return this.properties.getBucketName() + "/" + fileName;
  }

  public void deleteObject(final String objectName){
    this.s3Client.deleteObject(
      DeleteObjectRequest.builder()
        .bucket(this.properties.getBucketName())
        .key(objectName)
        .build()
    );
  }

}

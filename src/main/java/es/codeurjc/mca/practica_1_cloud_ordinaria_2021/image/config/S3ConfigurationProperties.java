package es.codeurjc.mca.practica_1_cloud_ordinaria_2021.image.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
public class S3ConfigurationProperties {

  @Value("amazon.s3.bucket-name")
  private String bucketName;

  @Value("amazon.s3.endpoint")
  private String endpoint;

  @Value("amazon.s3.region")
  private String region;

}

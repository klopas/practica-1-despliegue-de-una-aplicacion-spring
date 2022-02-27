package es.codeurjc.mca.practica_1_cloud_ordinaria_2021.image;

import java.io.IOException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("storageService")
@Profile("local")
@RequiredArgsConstructor
public class LocalS3ImageService implements ImageService {

    private final S3Service s3Service;

    @Override
    public String createImage(MultipartFile multiPartFile) {
        try {
            return this.s3Service.uploadFile(multiPartFile);
        } catch (IOException e) {
            log.error("ERROR al subir la imagen al bucket.", e);
            return null;
        }
    }

    @Override
    public void deleteImage(String image) {
        try {
            this.s3Service.deleteObject(image);
        } catch (Exception e) {
            log.error("ERROR al eliminar la imagen del bucket.", e);
        }
    }

}

package es.codeurjc.mca.practica_1_cloud_ordinaria_2021.image;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("storageService")
@Profile("production")
public class S3ImageService implements ImageService {

    @Override
    public String createImage(MultipartFile multiPartFile) {
        // Not implemented yet
        return null;
    }

    @Override
    public void deleteImage(String image) {
        // Not implemented yet
    }

}

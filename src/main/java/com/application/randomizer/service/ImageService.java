package com.application.randomizer.service;

import com.application.randomizer.domains.ImageDetails;
import com.application.randomizer.exceptions.ClientConnException;
import com.application.randomizer.repo.ImageDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class ImageService {

    @Autowired
    @Qualifier(value = "custom")
    private RestTemplate customRestTemplate;

    @Autowired
    @Qualifier(value = "default")
    private RestTemplate restTemplate;

    @Autowired
    private ImageDetailsRepo imageDetailsRepo;

    @Value("${picsum.url}")
    private String PRICSUM_URL;

    public List<ImageDetails> getAllImages() {
        return imageDetailsRepo.findAll();
    }

    public void deleteAllImages() {
        imageDetailsRepo.deleteAll();
    }

    public byte[] getImageById(Long id) {
        ImageDetails imageDetails = getRandomUrl(id);
        ResponseEntity<byte[]> imageResponse = safeGetResponse(() -> restTemplate.getForEntity(imageDetails.getImageUrl(), byte[].class));
        return imageResponse.getBody();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteImageById(List<Long> id) {
        imageDetailsRepo.deleteByImageIdIn(id);
    }


    public ImageDetails getRandomUrl(Long id) {
        Optional<ImageDetails> imageDetail = imageDetailsRepo.findByImageId(id);

        if (imageDetail.isPresent())
            return imageDetail.get();

        ResponseEntity<Object> entity = safeGetResponse(() -> customRestTemplate.getForEntity(PRICSUM_URL, Object.class));
        List<String> location = entity.getHeaders().get("Location");
        if (CollectionUtils.isEmpty(location))
            throw new RuntimeException("Server connection error");

        return imageDetailsRepo.save(new ImageDetails(id, location.get(0)));
    }

    private <T> ResponseEntity<T> safeGetResponse(Supplier<ResponseEntity<T>> restSupplier) {
        ResponseEntity<T> responseEntity = restSupplier.get();
        if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
            throw new ClientConnException("Error connecting the image provider");
        return responseEntity;
    }
}

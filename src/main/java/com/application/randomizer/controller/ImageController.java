package com.application.randomizer.controller;


import com.application.randomizer.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping()
    public ResponseEntity getImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @DeleteMapping()
    public ResponseEntity deleteImages() {
        imageService.deleteAllImages();
        return ResponseEntity.ok("Flushed..");
    }


    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageById(@PathVariable("id") String id) {
        Long imageId = Long.parseLong(id);
        return imageService.getImageById(imageId);
    }


    @DeleteMapping("/id")
    public ResponseEntity deleteByIds(@RequestBody List<Long> ids) {
        imageService.deleteImageById(ids);
        return ResponseEntity.ok("Deleted..");
    }

}

package com.id.akn.controller;

import com.id.akn.response.ApiRes;
import com.id.akn.service.ImageStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private ImageStorageService imageStorageService;
    @GetMapping
    public ResponseEntity<ApiRes> homeController(){
        ApiRes res=new ApiRes("Welcome To E-Commerce System", true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping("/slideimage")
    public ResponseEntity<List<String>> getHomeSlideImages() throws IOException {
        List<String> homeSlideImages = imageStorageService.getHomeSlideImages();
        return new ResponseEntity<>(homeSlideImages,HttpStatus.OK);
    }
}
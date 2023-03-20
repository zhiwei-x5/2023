package com.xzw.controller;


import com.xzw.domain.ResponseResult;
import com.xzw.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img) throws IOException {
        System.out.println("test17.56"+img);
        return uploadService.uploadImg(img);
    }
}

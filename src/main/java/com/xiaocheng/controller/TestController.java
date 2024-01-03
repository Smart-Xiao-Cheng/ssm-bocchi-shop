package com.xiaocheng.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class TestController {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @PostMapping("/upload")
    public String upload(MultipartFile uploadFile, HttpServletRequest request) throws IOException {
        String resourcePath = "/static/image/product/";
        ClassPathResource classPathResource = new ClassPathResource(resourcePath);
        File folder = classPathResource.getFile();
        String format = sdf.format(new Date());
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        uploadFile.transferTo(new File(folder, format + uploadFile.getOriginalFilename()));
        String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + resourcePath + format + uploadFile.getOriginalFilename();
        return filePath;
    }
}

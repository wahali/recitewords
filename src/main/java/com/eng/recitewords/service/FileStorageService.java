package com.eng.recitewords.service;

import com.eng.recitewords.config.FileStorageConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) throws IOException {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();
        Files.createDirectories(this.fileStorageLocation);
        /*try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            logger.info("无法在上传路径下创建目录", ex);
        }*/
    }

    public String storeFile(MultipartFile file) {
        // 标准化文件名
        String exp;
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // 检查非法字符
            if(fileName.contains("..")) {
                exp = "文件 " + fileName + " 存在非法字符！";
                logger.info(exp);
            }

            // 将文件复制到指定目录下
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            exp = "无法保存 " + fileName + "   请重试！/n原因如下： " + ex;
            logger.info(exp);
        }
        return exp;
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("找不到文件 " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("找不到文件 " + fileName, ex);
        }
    }

    private class MyFileNotFoundException extends RuntimeException {
        public MyFileNotFoundException(String message) {
            super(message);
        }

        public MyFileNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
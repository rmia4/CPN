package com.example.proj.domain.post.File;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private final String uploadDir = System.getProperty("user.dir") + "/upload/";

    @PostConstruct
    public void init() {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public List<String> saveFiles(List<MultipartFile> files) throws IOException {

        List<String> paths = new ArrayList<>();

        for (MultipartFile file : files) {

            if (file.isEmpty()) continue;

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File saveFile = new File(uploadDir + fileName);

            file.transferTo(saveFile);

            paths.add("/images/" + fileName);
        }

        return paths;
    }
}
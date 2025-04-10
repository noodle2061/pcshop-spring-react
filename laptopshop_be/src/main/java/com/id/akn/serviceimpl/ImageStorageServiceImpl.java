package com.id.akn.serviceimpl;

import com.id.akn.service.ImageStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {
    private final String baseDir = "src/main/resources/static/images/";
    private final String laptopDir = baseDir + "laptop/";

    @Override
    public Set<String> saveFiles(Integer laptopId, Set<MultipartFile> files) throws IOException {
        Set<String> imageUrls = new HashSet<>();

        Path laptopDirPath = Paths.get(laptopDir + laptopId);
        if (!Files.exists(laptopDirPath)) {
            Files.createDirectories(laptopDirPath);
        }

        for (MultipartFile file : files) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = laptopDirPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Tạo URL trả về cho frontend
            String imageUrl = "/images/laptop/" + laptopId + "/" + fileName;
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }

    @Override
    public void deleteFiles(Integer laptopId, Set<String> imageUrls) throws IOException {
        Path laptopDirPath = Paths.get(laptopDir + laptopId);
        for (String imageUrl : imageUrls) {
            String fileName = Paths.get(imageUrl).getFileName().toString();
            Path filePath = laptopDirPath.resolve(fileName);
            Files.deleteIfExists(filePath);
        }
    }

    @Override
    public void deleteLaptopDirectory(Integer laptopId) throws IOException {
        Path laptopDirPath = Paths.get(laptopDir + laptopId);
        if (Files.exists(laptopDirPath)) {
            try (var paths = Files.walk(laptopDirPath).sorted(Comparator.reverseOrder())) {
                paths.forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        throw new RuntimeException("Could not delete file: " + path, e);
                    }
                });
            }
        }
    }

    @Override
    public List<String> getHomeSlideImages() throws IOException {
        Path homeSlideDir = Paths.get(baseDir + "homeslide/");
        if (!Files.exists(homeSlideDir)) {
            return Collections.emptyList();
        }
        return Files.list(homeSlideDir)
                .filter(Files::isRegularFile)
                .map(path -> "/images/homeslide/" + path.getFileName().toString())
                .collect(Collectors.toList());
    }
}


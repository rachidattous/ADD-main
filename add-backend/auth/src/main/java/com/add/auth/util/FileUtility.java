package com.add.auth.util;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtility {

  public String getKey() {
    LocalDate now = LocalDate.now();
    return String.join("/", "Test",
        String.valueOf(now.getYear()),
        String.valueOf(now.getMonthValue()),
        String.valueOf(now.getDayOfMonth())

    ) + "/";
  }

  public String getExtention(String fileName) {

    return Optional.of(fileName)
        .filter(f -> f.contains("."))
        .map(f -> f.substring(fileName.lastIndexOf(".") + 1))
        .map(String::toLowerCase)
        .orElse("");

  }

  public String generateFileName(MultipartFile multipartFile, String fileId) {
    return fileId.concat(".").concat(getExtention(multipartFile.getOriginalFilename()));
  }

  public String join(String path1, String path2) {
    return Paths.get(path1, path2).toString();
  }

  public String getFileNameWithoutExtension(String fileName) {
    return fileName.replaceFirst("[.][^.]+$", "");
  }

}

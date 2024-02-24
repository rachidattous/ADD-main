package com.add.file.util;

import com.add.file.model.AFile;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

@UtilityClass
public class FileUtility {

  public String getKey() {
    LocalDate now = DateUtility.nowDate();
    return String.join("/", "file",
        String.valueOf(now.getYear()),
        String.valueOf(now.getMonthValue()),
        String.valueOf(now.getDayOfMonth())) + "/";
  }

  public String getOriginalFileNameFromFile(AFile file) {
    return Optional.ofNullable(file)
        .map(f -> f.getOriginalFileName() + "." + f.getExt())
        .orElse("");

  }

  public String getExtention(String fileName) {

    return Optional.ofNullable(fileName)
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

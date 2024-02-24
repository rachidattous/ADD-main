package com.add.file.services;

import com.add.file.constants.FileExtension;
import com.add.file.exception.file.FileNotFoundException;
import com.add.file.model.AFile;
import com.add.file.repository.AFileRepository;

import com.add.file.util.FileUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

  private final AFileRepository fileRepository;

  private final MinIOService minIOService;

  @Value("${minio.url}")
  private String url;

  public AFile uploadFile(MultipartFile fileToUpload, AFile file) {
    file.setExt(FileUtility.getExtention(fileToUpload.getOriginalFilename()));
    file.setExtension(FileExtension.getByValue(file.getExt()));
    file.setFileType(file.getExtension().getType());
    file.setOriginalFileName(FileUtility.getFileNameWithoutExtension(fileToUpload.getOriginalFilename()));
    file.setPath(minIOService.upload(fileToUpload, file.getId()));
    file.setUrl(url + file.getPath());
    file.setFileSize(fileToUpload.getSize());
    return file;

  }

  public Optional<AFile> getById(String id) {
    return fileRepository.findById(id);
  }

  public ResponseEntity<byte[]> downloadFile(String fileId) {
    log.info("download file with id : {}", fileId);
    return Optional.of(fileId)
        .map(fileRepository::findById)
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .map(this::downloadFile)
        .orElseThrow(FileNotFoundException::new);

  }

  public ResponseEntity<byte[]> downloadFile(AFile file) {
    log.info("download file with id : {}", file.getId());
    return minIOService.downloadFile(file);

  }

  public void deleteFile(String fileId) {
    log.info("delete file with id : {}", fileId);
    Optional.of(fileId)
        .map(fileRepository::findById)
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .ifPresent(this::deleteFile);

  }

  public void deleteFile(AFile file) {

    Optional.of(file)
        .ifPresent(e -> {
          minIOService.deleteFile(e.getPath());
          fileRepository.delete(e);
        });

  }

}

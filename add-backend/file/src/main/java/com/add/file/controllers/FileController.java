package com.add.file.controllers;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.add.file.model.AFile;
import com.add.file.services.FileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

  private final FileService fileService;

  @GetMapping("/download/{id}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable String id) {

    return fileService.downloadFile(id);

  }

  @DeleteMapping("/{id}")
  public void deleteFile(@PathVariable String id) {

    fileService.deleteFile(id);

  }

  @GetMapping("/{id}")
  public Optional<AFile> getFile(@PathVariable String id) {
    return fileService.getById(id);

  }

}

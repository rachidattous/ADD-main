package com.add.file.services;

import com.add.file.exception.file.FileDeleteException;
import com.add.file.exception.file.FileDownloadException;
import com.add.file.exception.file.FileInputStreamException;
import com.add.file.exception.file.FileUploadException;
import com.add.file.model.AFile;

import com.add.file.services.MinIOService;
import com.add.file.util.FileUtility;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinIOService {

  private final MinioClient minioClient;

  @Value("${minio.bucketName}")
  private String bucketName;

  @Value("${minio.url}")
  private String url;

  public String upload(MultipartFile file, String fileId) {

    try {
      String path = FileUtility.generateFileName(file, fileId);

      PutObjectArgs putObjectArgs = PutObjectArgs.builder()
          .contentType(file.getContentType())
          .object(path)
          .bucket(bucketName)
          .stream(file.getInputStream(), file.getSize(), 0)
          .build();

      minioClient.putObject(putObjectArgs);

      log.info("Succes uploading file {}", path);
      return path;

    } catch (Exception e) {
      log.error(e.getMessage());
      throw new FileUploadException();
    }
  }

  public ResponseEntity<byte[]> downloadFile(AFile file) {

    try {

      String headerValue = "attachment; filename=" + FileUtility.getOriginalFileNameFromFile(file);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      headers.set(HttpHeaders.CONTENT_DISPOSITION, headerValue);
      byte[] fileToDownload = getFileBytes(file);

      headers.setContentLength(fileToDownload.length);

      log.info("File {} is Downloaded", FileUtility.getOriginalFileNameFromFile(file));
      return ResponseEntity.ok().headers(headers).body(fileToDownload);

    } catch (Exception e) {
      log.error(e.getMessage());
      throw new FileDownloadException();
    }
  }

  public byte[] getFileBytes(AFile file) throws IOException {
    return IOUtils.toByteArray(getFileStream(file));
  }

  public InputStream getFileStream(AFile file) {

    try {
      GetObjectArgs getObjectArgs = GetObjectArgs.builder()
          .bucket(bucketName).object(file.getPath())
          .build();
      log.info("Getting File Stream");
      return minioClient.getObject(getObjectArgs);

    } catch (Exception e) {
      log.error(e.getMessage());
      throw new FileInputStreamException();
    }
  }

  public InputStream getFileStream(AFile file, long start, long lenth) {

    try {
      GetObjectArgs getObjectArgs = GetObjectArgs.builder()
          .bucket(bucketName).object(file.getPath())
          .offset(start).length(lenth)
          .build();
      log.info("Getting File Stream of {} start : {} end : {}", file.getId(), start, start + lenth);
      return minioClient.getObject(getObjectArgs);

    } catch (Exception e) {
      log.error(e.getMessage());
      throw new FileInputStreamException();
    }
  }

  public void deleteFile(String path) {

    try {
      RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucketName).object(path).build();
      minioClient.removeObject(removeObjectArgs);
      log.info("File {} is deleted", path);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new FileDeleteException();
    }

  }

}

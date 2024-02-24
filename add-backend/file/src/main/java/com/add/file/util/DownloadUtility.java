package com.add.file.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;

@Slf4j
@UtilityClass
public class DownloadUtility {

  public static ResponseEntity<byte[]> download(ByteArrayOutputStream baos, String fileName, String ext) {
    try {
      String headerValue = "attachment; filename=" + fileName + "." + ext;
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      headers.set(HttpHeaders.CONTENT_DISPOSITION, headerValue);

      byte[] bytes = baos.toByteArray();

      headers.setContentLength(bytes.length);

      log.info("File {} is Downloaded", fileName);
      return ResponseEntity.ok().headers(headers).body(bytes);

    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

  }

}

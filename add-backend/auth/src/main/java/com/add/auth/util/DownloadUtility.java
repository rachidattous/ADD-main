package com.add.auth.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class DownloadUtility {

  public static ResponseEntity<byte[]> download(ByteArrayOutputStream baos, String fileName, String ext)
      throws IOException {

    String headerValue = "attachment; filename=" + fileName + "." + ext;
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.set(HttpHeaders.CONTENT_DISPOSITION, headerValue);

    byte[] bytes = baos.toByteArray();

    headers.setContentLength(bytes.length);

    log.info("File {} is Downloaded", fileName);
    return ResponseEntity.ok().headers(headers).body(bytes);

  }

}


package com.add.file.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.add.file.constants.Constants;
import com.add.file.constants.FileType;
import com.add.file.exception.file.FileNotFoundException;
import com.add.file.exception.file.FileUnStreamableException;
import com.add.file.model.AFile;
import com.add.file.repository.AFileRepository;
import com.add.file.util.FileUtility;
import com.add.file.util.RangeUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class StreamService {

  private final AFileRepository fileRepository;

  private final MinIOService minIOService;

  public Mono<ResponseEntity<ByteArrayResource>> streamFile(String fileId, String httpRangeList) {

    AFile file = fileRepository.findById(fileId).orElseThrow(FileNotFoundException::new);
    if (file.getFileType().isStreamable()) {
      return Mono.just(prepareMedia(file, httpRangeList, file.getFileType().getValue()));
    } else if (file.getFileType().equals(FileType.DOCUMENT)) {
      return Mono.just(prepareDocument(file));
    }

    throw new FileUnStreamableException();

  }

  public long len(long rangeStart, long rangeEnd) {
    return (rangeEnd - rangeStart) + 1;
  }

  public ResponseEntity<ByteArrayResource> prepareMedia(AFile file, String range, String content) {

    try {
      RangeUtil ru = RangeUtil.getRangeUtil(range, file.getFileSize(), file.getFileType().isImage());
      return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
          .header("Content-Type", content + "/" + file.getExt())
          .header("Accept-Ranges", "bytes")
          .header("Content-Length", ru.lenStr())
          .header("Content-Range", "bytes" + " " + ru.getStart() + "-" + ru.getEnd() + "/" + file.getFileSize())
          .body(readByteRange(minIOService.getFileStream(file, ru.getStart(), ru.len()), ru.len()));

    } catch (Exception e) {
      log.error("Exception while reading the file {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

  }

  public ByteArrayResource readByteRange(InputStream inputStream, long len) throws IOException {

    ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream();
    byte[] data = new byte[Constants.BYTE_RANGE];
    int nRead;
    while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
      bufferedOutputStream.write(data, 0, nRead);
    }
    bufferedOutputStream.flush();

    byte[] result = new byte[(int) len];
    System.arraycopy(bufferedOutputStream.toByteArray(), 0, result, 0, result.length);
    return new ByteArrayResource(result);

  }

  public ResponseEntity<ByteArrayResource> prepareDocument(AFile file) {
    try {

      String headerValue = "inline; filename=" + FileUtility.getOriginalFileNameFromFile(file);
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application" + "/" + file.getExt());
      headers.set(HttpHeaders.CONTENT_DISPOSITION, headerValue);

      byte[] bytes = minIOService.getFileBytes(file);

      headers.setContentLength(bytes.length);

      return ResponseEntity
          .ok()
          .headers(headers)
          .body(new ByteArrayResource(bytes));
    } catch (Exception e) {
      log.error("Exception while reading the file {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

  }

}

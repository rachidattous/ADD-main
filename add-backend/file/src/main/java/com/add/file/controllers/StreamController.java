package com.add.file.controllers;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.add.file.services.StreamService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file/stream")
public class StreamController {

  private final StreamService streamService;

  @GetMapping("/{fileId}")
  @ResponseBody
  public Mono<ResponseEntity<ByteArrayResource>> stream(
      @RequestHeader(value = "Range", required = false) String range,
      @PathVariable String fileId) {

    return streamService.streamFile(fileId, range);
  }

}

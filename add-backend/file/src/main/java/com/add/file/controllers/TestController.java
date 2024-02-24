package com.add.file.controllers;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import com.add.file.dto.ContentDTO;
import com.add.file.services.TextExtractionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/file/test")
@RequiredArgsConstructor
public class TestController {

  private final TextExtractionService textExtractionService;

  @PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public String getHi(@ModelAttribute ContentDTO contentDTO) throws IOException {
    return textExtractionService.imageToText(contentDTO.getFile().getInputStream());
  }

}
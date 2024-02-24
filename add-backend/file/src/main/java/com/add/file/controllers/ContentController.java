package com.add.file.controllers;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.add.file.dto.ContentDTO;
import com.add.file.dto.ContentIdDTO;
import com.add.file.dto.SearchContentDTO;
import com.add.file.dto.ValidationDTO;
import com.add.file.model.Content;
import com.add.file.services.ContentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file/contents")
public class ContentController {

  private final ContentService contentService;

  @PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public Optional<Content> uploadContent(@Valid @ModelAttribute ContentDTO contentDTO) {
    return contentService.uploadContent(contentDTO);

  }

  @PutMapping(value = "/replace/{contentId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public Optional<Content> replaceContent(@PathVariable String contentId,
      @Valid @ModelAttribute ContentDTO contentDTO) {
    return contentService.replaceContent(contentId, contentDTO);

  }

  @PostMapping("/search")
  public Page<Content> searchContent(@RequestBody SearchContentDTO searchContentDTO, Pageable pageable) {
    return contentService.advancedSearch(searchContentDTO, pageable);

  }

  @PostMapping("/ids")
  public Page<Content> getByIds(@Valid @RequestBody ContentIdDTO contentIdDTO, Pageable pageable) {
    return contentService.getByIds(contentIdDTO, pageable);

  }

  @PostMapping("/validate/{id}")
  public Optional<Content> validateContent(@PathVariable String id, @Valid @RequestBody ValidationDTO validationDTO) {
    return contentService.validateContent(id, validationDTO);

  }

}

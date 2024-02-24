package com.add.search.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.add.search.dto.FileContentDTO;
import com.add.search.dto.QueryDTO;
import com.add.search.model.elasticSearch.FileContent;
import com.add.search.services.FileContentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/search/fileContent")
@RequiredArgsConstructor
public class SearchFileContentController {

  private final FileContentService fileContentService;

  @PostMapping
  public Optional<FileContent> createFileContent(@RequestBody FileContentDTO contentDTO) {

    return fileContentService.createFileContent(contentDTO);

  }

  @DeleteMapping("/{fileId}")
  public void deleteByFileId(@PathVariable String fileId) {

    fileContentService.deleteByFileId(fileId);

  }

  @GetMapping("/{fileId}")
  public Optional<FileContent> getByFileId(@PathVariable String fileId) {

    return fileContentService.getByFileId(fileId);

  }

  @PostMapping("/process")
  public Page<FileContent> fetchFileContent(@Valid @RequestBody QueryDTO query, Pageable pageable) {

    return fileContentService.processSearch(query, pageable);

  }

  @PostMapping("/process/ids")
  public List<String> fetchFileContentListIds(@Valid @RequestBody QueryDTO query) {

    return fileContentService.processSearchIds(query);

  }

  @PostMapping("/suggestions")
  public Page<String> fetchSuggestions(@Valid @RequestBody QueryDTO query, Pageable pageable) {

    return fileContentService.fetchSuggestions(query, pageable);

  }

}

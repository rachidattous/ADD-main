package com.add.file.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.add.file.dto.FileContentDTO;
import com.add.file.dto.QueryDTO;
import com.add.file.feignClient.ISearchRestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestService {

  private final ISearchRestService iSearchRestService;

  public Optional<FileContentDTO> pushToSearch(FileContentDTO fileContentDTO) {
    try {
      log.info("sending this file to search {}", fileContentDTO);
      return iSearchRestService.createFileContentSearch(fileContentDTO);
    } catch (Exception e) {
      log.error(e.getMessage());
      return Optional.empty();
    }
  }

  public List<String> getSearchByQuery(QueryDTO query) {
    try {
      return iSearchRestService.fetchFileContent(query);
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ArrayList<>();
    }
  }

  public void deleteFileContentByFileId(String fileId) {
    try {
      iSearchRestService.deleteByFileId(fileId);
    } catch (Exception e) {
      log.error(e.getMessage());

    }
  }

  public Page<FileContentDTO> toPage(Pageable pageable) {
    return new PageImpl<>(new ArrayList<>(), pageable, 0);

  }
}

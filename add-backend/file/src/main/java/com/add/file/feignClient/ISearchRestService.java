package com.add.file.feignClient;

import org.springframework.cloud.openfeign.FeignClient;

import com.add.file.dto.FileContentDTO;
import com.add.file.dto.QueryDTO;
import com.add.file.feignClient.config.FeignClientConfig;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "search", configuration = FeignClientConfig.class)
public interface ISearchRestService {

  @PostMapping(value = "/api/search/fileContent")
  public Optional<FileContentDTO> createFileContentSearch(@RequestBody FileContentDTO contentDTO);

  @PostMapping(value = "/api/search/fileContent/process/ids")
  public List<String> fetchFileContent(@RequestBody QueryDTO query);

  @DeleteMapping("/api/search/fileContent/{fileId}")
  public void deleteByFileId(@PathVariable String fileId);

}

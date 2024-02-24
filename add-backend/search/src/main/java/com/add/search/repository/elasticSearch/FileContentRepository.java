package com.add.search.repository.elasticSearch;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.add.search.model.elasticSearch.FileContent;

public interface FileContentRepository extends ElasticsearchRepository<FileContent, String> {

  void deleteByFileId(String fileId);

  Optional<FileContent> findByFileId(String fileId);
}

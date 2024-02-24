package com.add.search.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.add.search.dto.FileContentDTO;
import com.add.search.dto.QueryDTO;
import com.add.search.model.elasticSearch.FileContent;
import com.add.search.repository.elasticSearch.FileContentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class FileContentService {

	private final ElasticsearchOperations elasticsearchOperations;

	private final FileContentRepository fileContentRepository;

	public FileContent toEntity(FileContentDTO fileContentDTO) {
		return FileContent.builder()
				.fileId(fileContentDTO.getFileId())
				.content(fileContentDTO.getContent())
				.description(fileContentDTO.getDescription())
				.title(fileContentDTO.getTitle())
				.ext(fileContentDTO.getExt())
				.originalFileName(fileContentDTO.getOriginalFileName())
				.fileType(fileContentDTO.getFileType())
				.build();
	}

	public Optional<FileContent> createFileContent(FileContentDTO fileContentDTO) {
		return Optional.of(fileContentRepository.save(toEntity(fileContentDTO)));

	}

	public List<FileContent> createFileContents(List<FileContentDTO> fileContentDTO) {
		return fileContentDTO
				.stream()
				.map(this::createFileContent)
				.filter(e -> e.isPresent())
				.map(e -> e.get())
				.collect(Collectors.toList());
	}

	public Page<FileContent> processSearch(QueryDTO queryDTO, Pageable pageable) {

		QueryBuilder queryBuilder = QueryBuilders
				.multiMatchQuery(queryDTO.query(), queryDTO.criteria())
				.fuzziness(Fuzziness.AUTO);

		Query searchQuery = new NativeSearchQueryBuilder()
				.withFilter(queryBuilder)
				.withPageable(pageable)
				.build();

		return toPage(elasticsearchOperations
				.search(searchQuery, FileContent.class, IndexCoordinates.of(FileContent.INDEX)), pageable);

	}

	public List<String> processSearchIds(QueryDTO queryDTO) {

		QueryBuilder queryBuilder = QueryBuilders
				.multiMatchQuery(queryDTO.query(), queryDTO.criteria())
				.fuzziness(Fuzziness.AUTO);

		Query searchQuery = new NativeSearchQueryBuilder()
				.withFilter(queryBuilder)
				.build();

		return elasticsearchOperations
				.search(searchQuery, FileContent.class, IndexCoordinates.of(FileContent.INDEX))
				.stream()
				.map(searchHit -> searchHit.getContent())
				.map(e -> e.getFileId())
				.collect(Collectors.toList());

	}

	public Page<String> fetchSuggestions(QueryDTO queryDTO, Pageable pageable) {

		return processSearch(queryDTO, pageable)
				.map(FileContent::getTitle);

	}

	public Page<FileContent> toPage(SearchHits<FileContent> searchHints, Pageable pageable) {
		return new PageImpl<>(searchHints.stream()
				.map(searchHit -> searchHit.getContent())
				.collect(Collectors.toList()), pageable, searchHints.getTotalHits());

	}

	public void deleteByFileId(String fileId) {
		fileContentRepository.deleteByFileId(fileId);
		log.info("content with fileId {} is deleted ", fileId);

	}

	public Optional<FileContent> getByFileId(@Valid @NotNull @NotEmpty String fileId) {
		return fileContentRepository.findByFileId(fileId);

	}

}

package com.add.file.services;

import com.add.file.constants.ContentState;
import com.add.file.dto.ContentDTO;
import com.add.file.dto.ContentIdDTO;
import com.add.file.dto.ContentsDTO;
import com.add.file.dto.FileContentDTO;
import com.add.file.dto.QueryDTO;
import com.add.file.dto.SearchContentDTO;
import com.add.file.dto.ValidationDTO;
import com.add.file.exception.content.ContentNotFoundException;
import com.add.file.exception.content.ContentUploadException;
import com.add.file.model.Content;
import com.add.file.model.Validation;
import com.add.file.repository.ContentRepository;
import com.add.file.repository.ValidationRepository;
import com.add.file.services.ContentService;
import com.add.file.specification.ContentSpecification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentService {

  private final ContentRepository contentRepository;

  private final FileService fileService;

  private final ValidationRepository validationRepository;

  private final RestService restService;

  private final TextExtractionService textExtractionService;

  private final ModelMapper modelMapper;

  public Page<Content> search(SearchContentDTO searchContentDTO, Pageable pageable) {

    return contentRepository.findAll(ContentSpecification.searchRequest(searchContentDTO), pageable);

  }

  public Page<Content> advancedSearch(SearchContentDTO searchContentDTO, Pageable pageable) {
    if (searchContentDTO.getQuery() != null && !searchContentDTO.getQuery().trim().equals("")) {
      List<String> ids = restService.getSearchByQuery(QueryDTO.builder().query(searchContentDTO.getQuery()).build());
      searchContentDTO.setFileIds(ids);
    }
    return search(searchContentDTO, pageable);

  }

  public void deleteContent(String contentId) {

    contentRepository.findById(contentId)
        .ifPresent(this::deleteContent);

  }

  public void deleteContent(Content content) {
    String id = content.getId();
    fileService.deleteFile(content);
    restService.deleteFileContentByFileId(id);
  }

  public Optional<Content> uploadContent(ContentDTO contentDTO) {
    log.info("user = {}", contentDTO.getUserId());
    Content file = new Content();
    fileService.uploadFile(contentDTO.getFile(), file);
    file.setUserId(contentDTO.getUserId());
    file.setDescription(contentDTO.getDescription());
    file.setTitle(contentDTO.getTitle());
    contentRepository.save(file);
    extractAndSendToSearch(file, contentDTO);
    log.info("Content with id {}  was uploaded with success", file.getId());
    return Optional.of(file);

  }

  @Async
  public void extractAndSendToSearch(Content file, ContentDTO contentDTO) {
    try {

      log.info("Content {} text extraction is started ", file.getId());
      FileContentDTO search = FileContentDTO.builder()
          .description(file.getDescription())
          .fileType(file.getFileType().getValue())
          .fileId(file.getId())
          .title(file.getTitle())
          .originalFileName(file.getOriginalFileName())
          .ext(file.getExt())
          .content(textExtractionService.extract(file, contentDTO))
          .build();
      log.info("Content {} text extraction is ended ", file.getId());

      restService.pushToSearch(search);
      log.info("Content {} is pushed to seach service ", file.getId());
    } catch (Exception e) {
      log.error(e.getMessage());
    }

  }

  public Page<Content> getByIds(ContentIdDTO contentIds, Pageable pageable) {
    return contentRepository.findByIdIn(contentIds.getContentIds(), pageable);

  }

  public List<Content> uploadContents(ContentsDTO contentsDTO) {

    return contentsDTO.getContents()
        .stream()
        .map(this::uploadContent)
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .collect(Collectors.toList());

  }

  public ResponseEntity<byte[]> downloadUserPhoto(String contentId) {
    return contentRepository.findById(contentId)
        .map(fileService::downloadFile)
        .orElseThrow(ContentNotFoundException::new);

  }

  public Optional<Content> validateContent(String contentId, ValidationDTO validationDTO) {

    return contentRepository.findById(contentId)
        .map(e -> {
          Validation validation = modelMapper.map(validationDTO, Validation.class);
          validation.setContent(e);
          validation.setValidationOrder(e.getValidationOrder());
          validationRepository.save(validation);
          e.setState(ContentState.getByValidationType(validation.getValidationType()));

          return Optional.of(contentRepository.save(e));
        })
        .orElseThrow(ContentNotFoundException::new);

  }

  public Optional<Content> replaceContent(String contentId, ContentDTO contentDTO) {

    return contentRepository.findById(contentId)
        .map(e -> {
          Optional<Content> contentOptional = uploadContent(contentDTO);
          if (contentOptional.isPresent()) {
            Content content = contentOptional.get();
            List<Validation> validations = e.getValidations();
            validations.stream().forEach(v -> v.setContent(content));

            validationRepository.saveAll(validations);

            deleteContent(e);
            return Optional.of(contentRepository.save(content));
          }
          throw new ContentUploadException();
        })
        .orElseThrow(ContentNotFoundException::new);

  }
}

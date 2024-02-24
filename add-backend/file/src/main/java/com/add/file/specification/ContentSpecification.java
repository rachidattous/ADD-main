package com.add.file.specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.add.file.constants.ContentState;
import com.add.file.constants.FileType;
import com.add.file.dto.SearchContentDTO;
import com.add.file.model.Content;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ContentSpecification {

  public String like(String arg) {
    return Optional.ofNullable(arg).map(e -> "%" + e + "%").orElse("");
  }

  private Specification<Content> distinct() {
    return (root, query, builder) -> {
      query.distinct(true);
      return builder.isNotNull(root.get("id"));
    };
  }

  private Specification<Content> hasState(ContentState state) {
    if (state == null) {
      return null;
    }
    return (root, query, builder) -> builder.equal(root.get("state"), state);

  }

  private Specification<Content> hasIds(List<String> ids) {
    if (ids == null) {
      return null;
    }
    return (root, query, builder) -> builder.and(root.get("id").in(ids));

  }

  private Specification<Content> hasUserId(String userId) {
    if (userId == null) {
      return null;
    }
    return (root, query, builder) -> builder.equal(root.get("userId"), userId);

  }

  private Specification<Content> hasDate(LocalDate date) {
    if (date == null) {
      return null;
    }
    return (root, query, builder) -> builder.equal(root.get("uploadDate"), date);

  }

  private Specification<Content> hasTime(LocalTime time) {
    if (time == null) {
      return null;
    }
    return (root, query, builder) -> builder.equal(root.get("uploadTime"), time);

  }

  private Specification<Content> hasFileType(FileType fileType) {
    if (fileType == null) {
      return null;
    }
    return (root, query, builder) -> builder.equal(root.get("fileType"), fileType);

  }

  private Specification<Content> hasExtension(String ext) {
    if (ext == null) {
      return null;
    }
    return (root, query, builder) -> builder.equal(root.get("ext"), ext);

  }

  public Specification<Content> searchRequest(SearchContentDTO searchContentDTO) {

    return Specification.where(distinct())
        .and(hasUserId(searchContentDTO.getUserId()))
        .and(hasIds(searchContentDTO.getFileIds()))
        .and(hasDate(searchContentDTO.getDate()))
        .and(hasTime(searchContentDTO.getTime()))
        .and(hasFileType(searchContentDTO.getFileType()))
        .and(hasExtension(searchContentDTO.getExt()))
        .and(hasState(searchContentDTO.getState()));

  }

}

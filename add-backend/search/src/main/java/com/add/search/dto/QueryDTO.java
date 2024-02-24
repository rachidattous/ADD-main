package com.add.search.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.add.search.exception.ApiException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryDTO {

  @NotBlank
  @NotNull
  private String query;

  private List<String> criteria;

  public String[] criteria() {
    if (criteria == null || criteria.isEmpty()) {
      String[] array = {
          "title", "content", "description",
          "dateStored", "timeStored", "ext", "originalFileName", "fileType"
      };
      return array;
    }
    return (String[]) criteria.toArray();
  }

  public String query() {
    if (query != null && !query.trim().equals("")) {
      return query + "*";
    }
    throw new ApiException("query sould not be null or blank");
  }

}

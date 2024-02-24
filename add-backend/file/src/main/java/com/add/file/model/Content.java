package com.add.file.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.add.file.constants.ContentState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Content extends AFile {

  private String userId;

  private ContentState state = ContentState.PENDING;

  private String title;

  private String description;

  @OneToMany(mappedBy = "content")
  private List<Validation> validations;

  public Long getValidationOrder() {
    if (validations == null || validations.isEmpty()) {
      return 0l;
    } else {
      return (long) validations.size();
    }

  }

}

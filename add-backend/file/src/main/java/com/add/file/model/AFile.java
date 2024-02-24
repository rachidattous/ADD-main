package com.add.file.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.add.file.constants.FileExtension;
import com.add.file.constants.FileType;
import com.add.file.util.DateUtility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AFile {

  @Id
  private String id = UUID.randomUUID().toString();

  private String url;

  private String ext;

  private FileExtension extension;

  private LocalDate uploadDate = DateUtility.nowDate();

  private LocalTime uploadTime = DateUtility.nowTime();

  private String originalFileName;

  private String path;

  private Long fileSize;

  private FileType fileType;

  @Override
  public String toString() {
    return "AFile [id=" + id + ", url=" + url + ", ext=" + ext + ", extension=" + extension + ", uploadDate="
        + uploadDate + ", uploadTime=" + uploadTime + ", originalFileName=" + originalFileName + ", path=" + path
        + ", fileSize=" + fileSize + ", fileType=" + fileType + "]";
  }

}

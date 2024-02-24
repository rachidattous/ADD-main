package com.add.search.model.elasticSearch;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.add.search.utils.DateUtility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = FileContent.INDEX)
public class FileContent {

  public static final String INDEX = "content";

  @Id
  @Builder.Default
  private String id = UUID.randomUUID().toString();

  @Field(type = FieldType.Text, name = "fileId")
  private String fileId;

  @Field(type = FieldType.Text, name = "title")
  private String title;

  @Field(type = FieldType.Text, name = "content")
  private String content;

  @Field(type = FieldType.Text, name = "description")
  private String description;

  @Builder.Default
  @Field(type = FieldType.Text, name = "dateStored")
  private String dateStored = DateUtility.nowDate().toString();

  @Builder.Default
  @Field(type = FieldType.Text, name = "timeStored")
  private String timeStored = DateUtility.nowTime().toString();

  @Field(type = FieldType.Text, name = "ext")
  private String ext;

  @Field(type = FieldType.Text, name = "originalFileName")
  private String originalFileName;

  @Field(type = FieldType.Text, name = "fileType")
  private String fileType;

}

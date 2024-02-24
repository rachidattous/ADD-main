package com.add.auth.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

  @Id
  @Builder.Default
  private String id = UUID.randomUUID().toString();

  private String name;

  private String description;

  @ManyToMany
  private List<Permission> permissions;

}

package com.add.auth.model;

import com.add.auth.constants.Permissions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permission {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    private Permissions permission;

    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

}

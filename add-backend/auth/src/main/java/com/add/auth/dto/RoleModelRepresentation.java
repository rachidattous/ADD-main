package com.add.auth.dto;

import org.keycloak.representations.idm.RoleRepresentation;

import com.add.auth.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleModelRepresentation {

  private Role model;

  private RoleRepresentation representation;

}

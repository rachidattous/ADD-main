package com.add.auth.services;

import com.add.auth.repository.RoleRepository;

import com.add.auth.util.Utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.add.auth.dto.AFileDTO;
import com.add.auth.dto.RoleDTO;
import com.add.auth.dto.RoleModelRepresentation;

import com.add.auth.exception.role.RoleNullException;
import com.add.auth.model.Permission;
import com.add.auth.model.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import org.keycloak.admin.client.Keycloak;

import org.keycloak.admin.client.resource.RolesResource;

import org.keycloak.representations.idm.RoleRepresentation;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

  @Value("${keycloak.realm}")
  private String realm;

  private final Keycloak keycloak;

  private final RoleRepository roleRepository;

  private final PermissionService permissionService;

  private final ExportService exportService;

  private final ImportService importService;

  public RolesResource getRolesResources() {
    return keycloak.realm(realm).roles();
  }

  public Optional<RoleRepresentation> getRoleByName(String roleName) {
    try {
      return Optional.of(roleName)
          .map(String::toUpperCase)
          .map(e -> getRolesResources().get(e))
          .map(e -> Optional.of(e.toRepresentation()))
          .orElse(Optional.empty());
    } catch (Exception e) {
      return Optional.empty();
    }

  }

  public Optional<RoleModelRepresentation> getRoleRepresenationById(String roleId) {
    return Optional.of(roleId)
        .map(roleRepository::findById)
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .map(e -> Optional.of(new RoleModelRepresentation(e, getRolesResources().get(e.getName()).toRepresentation())))
        .orElse(Optional.empty());
  }

  public List<RoleRepresentation> getByRoles(List<Role> roles) {
    return roles
        .stream()
        .map(role -> getRoleByName(role.getName()))
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .collect(Collectors.toList());
  }

  public List<RoleRepresentation> getAllRoles() {
    return getRolesResources().list();

  }

  public Page<Role> getRoles(Pageable pageable) {
    return roleRepository.findAll(pageable);

  }

  public List<Role> getRoles() {
    return roleRepository.findAll();

  }

  public List<RoleRepresentation> getByUserId(String userId) {
    try {
      return keycloak
          .realm(realm)
          .users()
          .get(userId)
          .roles()
          .realmLevel()
          .listAll();
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ArrayList<>();
    }

  }

  public Optional<RoleRepresentation> createRoleRepresentation(Role role) {
    return Optional.of(role)
        .map(e -> {
          Optional<RoleRepresentation> roleFound = getRoleByName(e.getName().toUpperCase());
          if (roleFound.isPresent()) {
            return roleFound;
          }
          RoleRepresentation roleRepresentation = new RoleRepresentation();
          roleRepresentation.setName(role.getName());
          roleRepresentation.setDescription(role.getDescription());
          getRolesResources().create(roleRepresentation);
          return Optional.of(roleRepresentation);
        })
        .orElseThrow(RoleNullException::new);

  }

  public Optional<Role> createRole(RoleDTO roleDTO) {

    Optional<Role> roleFound = roleRepository.findByName(roleDTO.getName());
    if (!roleFound.isPresent()) {

      Role role = Role.builder()
          .name(roleDTO.getName().toUpperCase())
          .description(roleDTO.getDescription())
          .permissions(permissionService.getByPermessions(roleDTO.getPermissions()))
          .build();

      roleRepository.save(role);
      createRoleRepresentation(role);
      return Optional.of(role);

    } else {
      return roleFound;
    }
  }

  public List<Role> getUserRoles(String userId) {
    List<String> kRoles = getByUserId(userId)
        .stream()
        .map(RoleRepresentation::getName)
        .collect(Collectors.toList());

    return roleRepository.findAll()
        .stream()
        .filter(role -> kRoles.contains(role.getName()))
        .collect(Collectors.toList());

  }

  public List<String> rolesToString(List<Role> roles) {
    return roles.stream().map(Role::getName).collect(Collectors.toList());
  }

  public List<Role> stringToRoles(List<String> roles) {
    if (roles == null || roles.isEmpty()) {
      return new ArrayList<>();
    }

    return roleRepository.findByNameIn(roles);

  }

  public List<Role> stringToRoles2(List<String> roles) {
    if (roles == null || roles.isEmpty()) {
      return new ArrayList<>();
    }

    return roles
        .stream().map(roleRepository::findByName)
        .filter(e -> e.isPresent())
        .map(e -> e.get()).collect(Collectors.toList());

  }

  public Optional<Role> updateRole(String roleId, RoleDTO roleDTO) {
    return Optional.of(roleId)
        .map(roleRepository::findById)
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .map(role -> {
          role.setName(roleDTO.getName());
          role.setDescription(roleDTO.getDescription());
          role.setPermissions(permissionService.getByPermessions(roleDTO.getPermissions()));
          return Optional.of(roleRepository.save(role));
        })
        .orElse(Optional.empty());
  }

  public Optional<Role> updateRole(RoleDTO roleDTO) {
    return roleRepository.findById(roleDTO.getName().toUpperCase())
        .map(role -> {
          role.setName(roleDTO.getName());
          role.setDescription(roleDTO.getDescription());
          role.setPermissions(permissionService.getByPermessions(roleDTO.getPermissions()));
          return Optional.of(roleRepository.save(role));
        })
        .orElse(Optional.empty());
  }

  public void deleteRole(String roleId) {
    Optional.of(roleId)
        .ifPresent(roleRepository::deleteById);

  }

  public Optional<Role> grantPermission(String roleId, String permissionId) {
    return Optional.of(roleId)
        .map(this::getById)
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .map(e -> grantPermission(e, permissionService.getById(permissionId).orElse((null))))
        .orElse(Optional.empty());

  }

  public Optional<Role> grantPermission(Role role, Permission permission) {
    role.setPermissions(Utils.addToList(role.getPermissions(), permission));
    return Optional.of(roleRepository.save(role));

  }

  public Optional<Role> revokePermission(String roleId, String permissionId) {
    return Optional.of(roleId)
        .map(this::getById)
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .map(e -> revokePermission(e, permissionService.getById(permissionId).orElse((null))))
        .orElse(Optional.empty());

  }

  public Optional<Role> revokePermission(Role role, Permission permission) {
    role.setPermissions(Utils.removeFromList(role.getPermissions(), permission));
    return Optional.of(roleRepository.save(role));

  }

  public Optional<Role> getById(String roleId) {
    return roleRepository.findById(roleId);
  }

  public ResponseEntity<byte[]> export() {
    return exportService.exportRoleExcel(getRoles());
  }

  public Optional<Role> createOrUpdate(RoleDTO roleDTO) {

    if (!roleRepository.existsByName(roleDTO.getName().toUpperCase())) {

      return createRole(roleDTO);
    } else {

      return updateRole(roleDTO);
    }
  }

  public List<Role> importRoles(AFileDTO fileDTO) {
    return importService.importRoles(fileDTO)
        .stream()
        .map(this::createOrUpdate)
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .collect(Collectors.toList());
  }

}

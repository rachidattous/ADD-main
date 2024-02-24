package com.add.auth.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.add.auth.dto.AFileDTO;
import com.add.auth.dto.RoleDTO;
import com.add.auth.model.Role;
import com.add.auth.services.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;

  @GetMapping
  public Page<Role> getAllRoles(Pageable pageable) {
    return roleService.getRoles(pageable);
  }

  @GetMapping("/list")
  public List<Role> getRoles() {
    return roleService.getRoles();
  }

  @GetMapping("/export/xls")
  public ResponseEntity<byte[]> export() {
    return roleService.export();
  }

  @PutMapping(value = "/import/xls", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })

  public List<Role> importRoles(@ModelAttribute AFileDTO fileDTO) {
    return roleService.importRoles(fileDTO);
  }

  @PatchMapping("/{roleId}")
  public Optional<Role> updateUserData(@RequestBody RoleDTO roleDTO, @PathVariable String roleId) {
    return roleService.updateRole(roleId, roleDTO);
  }

  @DeleteMapping("/{roleId}")
  public void deleteRole(@PathVariable String roleId) {
    roleService.deleteRole(roleId);
  }

  @GetMapping("/{roleId}")
  public Optional<Role> getRole(@PathVariable String roleId) {
    return roleService.getById(roleId);
  }

  @PostMapping
  public Optional<Role> createRole(@RequestBody RoleDTO roleDTO) {
    return roleService.createRole(roleDTO);
  }

  @PostMapping("/{roleId}/grant/{permissionId}")
  public Optional<Role> grantPermission(@PathVariable String roleId, @PathVariable String permissionId) {
    return roleService.grantPermission(roleId, permissionId);
  }

  @DeleteMapping("/{roleId}/revoke/{permissionId}")
  public Optional<Role> revokePermission(@PathVariable String roleId, @PathVariable String permissionId) {
    return roleService.revokePermission(roleId, permissionId);
  }

}

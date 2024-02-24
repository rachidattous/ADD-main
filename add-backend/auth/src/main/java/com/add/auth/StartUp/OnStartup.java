package com.add.auth.startUp;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.add.auth.constants.Permissions;
import com.add.auth.dto.RoleDTO;

import com.add.auth.services.PermissionService;
import com.add.auth.services.RoleService;
import com.add.auth.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OnStartup {

  private final PermissionService permissionService;

  private final RoleService roleService;

  private final UserService userService;

  @PostConstruct
  public void insertPermissions() {
    try {
      Permissions.getAllPermissions()
          .stream()
          .forEach(permissionService::addPermission);

    } catch (Exception e) {
      log.error(e.getMessage());
    }

  }

  @PostConstruct
  public void insertAdminRole() {
    try {
      roleService.createRole(RoleDTO.builder()
          .name("SUPER_ADMIN")
          .permissions(Arrays.asList(Permissions.values()))
          .build());
    } catch (Exception e) {
      log.error(e.getMessage());
    }

  }

  @PostConstruct
  public void insertAdmin() {
    try {
      userService.createAdminUser();
    } catch (Exception e) {
      log.error(e.getMessage());
    }

  }

}

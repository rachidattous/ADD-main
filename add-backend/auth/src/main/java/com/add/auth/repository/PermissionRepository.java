package com.add.auth.repository;

import com.add.auth.constants.Permissions;
import com.add.auth.model.Permission;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {

    Optional<Permission> findByPermission(Permissions permission);

    List<Permission> findByPermissionIn(List<Permissions> permission);

    boolean existsByPermission(Permissions permission);
}

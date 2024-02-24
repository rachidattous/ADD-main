package com.add.auth.services;

import com.add.auth.constants.Permissions;
import com.add.auth.dto.AFileDTO;
import com.add.auth.dto.PermissionDTO;

import com.add.auth.model.Permission;
import com.add.auth.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    private final ExportService exportService;

    private final ImportService importService;

    public Page<Permission> getAll(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }

    public List<Permission> getAll() {
        return permissionRepository.findAll();
    }

    public Optional<Permission> getById(String id) {
        return permissionRepository.findById(id);
    }

    public Optional<Permission> getByPermession(Permissions permission) {
        return Optional.of(permission)
                .map(e -> permissionRepository.findByPermission(permission))
                .orElse(Optional.empty());
    }

    public List<Permission> getByPermessions(List<Permissions> permissions) {
        return permissionRepository.findByPermissionIn(permissions);
    }

    public Optional<Permission> addPermission(Permissions permission) {

        return Optional.of(permission)
                .filter(e -> !permissionRepository.existsByPermission(e))
                .map(e -> Optional.of(permissionRepository.save(Permission.builder().permission(e).build())))
                .orElse(Optional.empty());
    }

    public List<Permission> addPermissions(List<Permissions> permissions) {

        return permissions.stream()
                .map(this::addPermission)
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .collect(Collectors.toList());
    }

    public void deletePermissionById(String id) {

        Optional.of(id)
                .map(permissionRepository::findById)
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .ifPresent(this::deletePermission);

    }

    public void deletePermission(Permission permession) {

        Optional.of(permession)
                .ifPresent(permissionRepository::delete);

    }

    public Optional<Permission> updatePermission(String id, PermissionDTO permissionDTO) {
        return Optional.of(id)
                .map(permissionRepository::findById)
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {
                    e.setDescription(permissionDTO.getDescription());
                    return Optional.of(permissionRepository.save(e));
                })
                .orElse(Optional.empty());

    }

    public Optional<Permission> updatePermission(PermissionDTO permissionDTO) {
        return permissionRepository.findByPermission(permissionDTO.getPermission())
                .map(e -> {
                    e.setDescription(permissionDTO.getDescription());
                    return Optional.of(permissionRepository.save(e));
                })
                .orElse(Optional.empty());

    }

    public ResponseEntity<byte[]> export() {
        return exportService.exportPermissionsExcel(getAll());
    }

    public List<Permission> importPermissions(AFileDTO fileDTO) {
        return importService.importPermissions(fileDTO)
                .stream()
                .map(this::updatePermission)
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .collect(Collectors.toList());
    }

}

package com.add.auth.controllers;

import com.add.auth.dto.AFileDTO;
import com.add.auth.dto.PermissionDTO;
import com.add.auth.model.Permission;
import com.add.auth.services.PermissionService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public Page<Permission> getAllPermissions(Pageable pageable) {
        return permissionService.getAll(pageable);
    }

    @GetMapping("/list")
    public List<Permission> getAllPermissions() {
        return permissionService.getAll();
    }

    @GetMapping("/export/xls")
    public ResponseEntity<byte[]> export() {
        return permissionService.export();
    }

    @GetMapping("/{id}")
    public Optional<Permission> getPermissionByName(@PathVariable String id) {
        return permissionService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePermission(@PathVariable String id) {
        permissionService.deletePermissionById(id);
    }

    @PatchMapping("/{id}")
    public Optional<Permission> updatePermission(@PathVariable String id, @RequestBody PermissionDTO permissionDTO) {
        return permissionService.updatePermission(id, permissionDTO);
    }

    @PutMapping(value = "/import/xls", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public List<Permission> importPermissions(@ModelAttribute AFileDTO fileDTO) {
        return permissionService.importPermissions(fileDTO);
    }

}

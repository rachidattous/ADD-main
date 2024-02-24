package com.add.auth.services;

import com.add.auth.constants.Permissions;
import com.add.auth.exception.IX.ExportingExcelException;
import com.add.auth.model.Permission;
import com.add.auth.model.Role;
import com.add.auth.model.UserData;

import com.add.auth.util.DateUtility;
import com.add.auth.util.DownloadUtility;
import com.add.auth.util.ExcelExportUtility;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExportService {

    public void createRoleLines(WritableSheet sheet, List<Role> roles) {
        AtomicInteger row = new AtomicInteger(1);
        roles.stream().filter(Objects::nonNull).forEach(role -> createRoleLine(role, sheet, row));

    }

    public void createRoleLine(Role role, WritableSheet sheet, AtomicInteger row) {

        int col = 0;
        ExcelExportUtility.addLabel(sheet, col++, row.get(), role.getName());
        ExcelExportUtility.addLabel(sheet, col++, row.get(), role.getDescription());
        if (role.getPermissions() != null) {
            ExcelExportUtility.addLabel(sheet, col, row.get(), role.getPermissions().stream()
                    .map(Permission::getPermission).map(Permissions::getValue)
                    .collect(Collectors.joining(",")));
        }
        row.getAndIncrement();

    }

    public ResponseEntity<byte[]> exportRoleExcel(List<Role> roles) {
        try {
            long start = System.currentTimeMillis();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("fr", "FR"));
            WritableWorkbook workbook = Workbook.createWorkbook(baos, wbSettings);
            workbook.createSheet("role", 0);
            WritableSheet sheet = workbook.getSheet(0);

            ExcelExportUtility.createHeader(sheet, Arrays.asList("Nom", "Description", "Permissions"));
            createRoleLines(sheet, roles);

            workbook.write();
            workbook.close();

            long end = System.currentTimeMillis();

            log.info("time taken to export roles is {}s  ", (end - start) / 1000);
            return DownloadUtility.download(baos, "Roles_" + DateUtility.nowDateTime().toString(), "xls");
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new ExportingExcelException();
        }

    }

    public void createPrmissionLines(WritableSheet sheet, List<Permission> permissions) {
        AtomicInteger row = new AtomicInteger(1);
        permissions
                .stream()
                .filter(Objects::nonNull)
                .forEach(permission -> createPermissionLine(permission, sheet, row));

    }

    public void createPermissionLine(Permission permission, WritableSheet sheet, AtomicInteger row) {

        int col = 0;
        ExcelExportUtility.addLabel(sheet, col++, row.get(), permission.getPermission().getValue());
        ExcelExportUtility.addLabel(sheet, col, row.get(), permission.getDescription());
        row.getAndIncrement();

    }

    public ResponseEntity<byte[]> exportPermissionsExcel(List<Permission> permissions) {
        try {
            long start = System.currentTimeMillis();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("fr", "FR"));
            WritableWorkbook workbook = Workbook.createWorkbook(baos, wbSettings);
            workbook.createSheet("permission", 0);
            WritableSheet sheet = workbook.getSheet(0);

            ExcelExportUtility.createHeader(sheet, Arrays.asList("Nom", "Description"));
            createPrmissionLines(sheet, permissions);

            workbook.write();
            workbook.close();

            long end = System.currentTimeMillis();

            log.info("time taken to export roles is {}s ", (end - start) / 1000);
            return DownloadUtility.download(baos, "Permissions_" + DateUtility.nowDateTime().toString(), "xls");
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new ExportingExcelException();
        }

    }

    public void createUserLines(WritableSheet sheet, List<UserData> users) {
        AtomicInteger row = new AtomicInteger(1);
        users
                .stream()
                .filter(Objects::nonNull)
                .forEach(user -> createUserLine(user, sheet, row));

    }

    public void createUserLine(UserData user, WritableSheet sheet, AtomicInteger row) {

        int col = 0;
        ExcelExportUtility.addLabel(sheet, col++, row.get(), user.getUsername());
        ExcelExportUtility.addLabel(sheet, col++, row.get(), user.getFirstName());
        ExcelExportUtility.addLabel(sheet, col++, row.get(), user.getLastName());
        ExcelExportUtility.addLabel(sheet, col++, row.get(), user.getEmail());
        ExcelExportUtility.addLabel(sheet, col++, row.get(), user.getGender());
        ExcelExportUtility.addLabel(sheet, col++, row.get(), user.getBirthDate());
        ExcelExportUtility.addLabel(sheet, col++, row.get(), user.getJoinedDate());
        ExcelExportUtility.addLabel(sheet, col++, row.get(), user.isEmailVerified());
        ExcelExportUtility.addLabel(sheet, col, row.get(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.joining(",")));
        row.getAndIncrement();

    }

    public ResponseEntity<byte[]> exportUsersExcel(List<UserData> users) {
        try {
            long start = System.currentTimeMillis();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("fr", "FR"));
            WritableWorkbook workbook = Workbook.createWorkbook(baos, wbSettings);
            workbook.createSheet("user", 0);
            WritableSheet sheet = workbook.getSheet(0);

            ExcelExportUtility.createHeader(sheet, Arrays.asList("Username", "Prenom", "Nom", "Email", "Sexe",
                    "Date de Naissance", "Joined Date", "Status", "Roles"));
            createUserLines(sheet, users);

            workbook.write();
            workbook.close();

            long end = System.currentTimeMillis();

            log.info("time taken to export roles is {}s ", (end - start) / 1000);
            return DownloadUtility.download(baos, "User_" + DateUtility.nowDateTime().toString(), "xls");
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new ExportingExcelException();
        }

    }

}

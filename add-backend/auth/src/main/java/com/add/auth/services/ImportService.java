package com.add.auth.services;

import com.add.auth.constants.Gender;
import com.add.auth.constants.Permissions;
import com.add.auth.dto.AFileDTO;
import com.add.auth.dto.PermissionDTO;
import com.add.auth.dto.RoleDTO;

import com.add.auth.dto.UserInput;

import com.add.auth.util.Utils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportService {

    public String checkField(String field) {
        if (field == null || field.trim().equals("")) {
            return null;
        }
        return field.trim();
    }

    public List<String> readLine(Sheet sheet, int line) {

        return IntStream.range(0, sheet.getColumns())
                .mapToObj(column -> sheet.getCell(column, line))
                .map(Cell::getContents)
                .map(this::checkField)
                .collect(Collectors.toList());

    }

    public List<List<String>> readExcel(MultipartFile file) {
        try {

            InputStream in = file.getInputStream();
            Workbook workbook = Workbook.getWorkbook(in);
            Sheet sheet = workbook.getSheet(0);

            List<List<String>> rows = IntStream.range(1, sheet.getRows())
                    .mapToObj(line -> readLine(sheet, line))
                    // .filter(list -> Utils.checkEmptyStringList(list))
                    .collect(Collectors.toList());
            workbook.close();
            in.close();

            return rows;
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }

    }

    public PermissionDTO mapToPermissionDTO(List<String> line) {

        return PermissionDTO.builder()
                .permission(Permissions.getPermissionByValue(line.get(0)))
                .description(line.get(1))
                .build();
    }

    public List<PermissionDTO> importPermissions(AFileDTO fileDTO) {
        try {
            return readExcel(fileDTO.getFile())
                    .stream()
                    .map(this::mapToPermissionDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }

    }

    public RoleDTO mapToRoleDTO(List<String> line) {
        line.forEach(e -> log.info(e));
        return RoleDTO.builder()
                .name(line.get(0).toUpperCase())
                .description(line.get(1))
                .permissions(Arrays.asList(line.get(2).split(","))
                        .stream()
                        .map(Permissions::getPermissionByValue)
                        .filter(Utils::notNull)
                        .collect(Collectors.toList()))
                .build();
    }

    public List<RoleDTO> importRoles(AFileDTO fileDTO) {
        try {
            return readExcel(fileDTO.getFile())
                    .stream()
                    .map(this::mapToRoleDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }

    }

    public UserInput mapToUserInput(List<String> line) {
        return UserInput.builder()
                .username(line.get(0))
                .firstName(line.get(1))
                .lastName(line.get(2))
                .email(line.get(3))
                .gender(Gender.getByValue(line.get(4)))
                .birthDate(Utils.parseDateImport(line.get(5)))
                .joinedDate(Utils.parseDateImport(line.get(6)))
                .rolesName(Arrays.asList(line.get(8).split(",")))
                .build();
    }

    public List<UserInput> importUsers(AFileDTO fileDTO) {
        try {
            return readExcel(fileDTO.getFile())
                    .stream()
                    .map(this::mapToUserInput)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }

    }

}

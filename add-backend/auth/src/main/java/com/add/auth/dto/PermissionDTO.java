package com.add.auth.dto;

import com.add.auth.constants.Permissions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO {

        private Permissions permission;

        private String description;

}

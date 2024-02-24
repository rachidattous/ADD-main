package com.add.auth.dto;

import java.util.List;

import com.add.auth.constants.Permissions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

        private String name;

        private String description;

        private List<Permissions> permissions;
}

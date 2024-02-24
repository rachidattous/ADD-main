package com.add.auth.repository;

import com.add.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    List<Role> findByNameIn(List<String> roleNames);

    Optional<Role> findByName(String roleName);

    boolean existsByName(String name);

}

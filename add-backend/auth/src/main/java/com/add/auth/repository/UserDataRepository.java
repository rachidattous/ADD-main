package com.add.auth.repository;

import com.add.auth.constants.UserStatus;
import com.add.auth.model.UserData;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDataRepository extends JpaRepository<UserData, String>, JpaSpecificationExecutor<UserData> {

  Optional<UserData> findByUsername(String username);

  boolean existsByIdAndUserStatus(String id, UserStatus userStatus);

  boolean existsByUsername(String username);

}
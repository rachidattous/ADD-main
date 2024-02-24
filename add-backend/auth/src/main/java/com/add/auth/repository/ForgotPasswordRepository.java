package com.add.auth.repository;

import com.add.auth.model.ForgotPassword;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, String> {

  Optional<ForgotPassword> findByCode(String code);

  List<ForgotPassword> findByUserId(String userId);

}

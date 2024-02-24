package com.add.auth.repository;

import com.add.auth.model.VerifyEmail;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VerifyEmailRepository extends JpaRepository<VerifyEmail, String> {

  Optional<VerifyEmail> findByCode(String code);

  List<VerifyEmail> findByUserId(String userId);

}

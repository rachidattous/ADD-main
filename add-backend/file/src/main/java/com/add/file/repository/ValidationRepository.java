package com.add.file.repository;

import com.add.file.model.Validation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ValidationRepository extends JpaRepository<Validation, String>, JpaSpecificationExecutor<Validation> {

}

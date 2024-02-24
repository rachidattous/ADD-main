package com.add.file.repository;

import com.add.file.model.AFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AFileRepository extends JpaRepository<AFile, String> {

}

package com.add.file.repository;

import com.add.file.model.UserPhoto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPhotoRepository extends JpaRepository<UserPhoto, String> {

  Optional<UserPhoto> findTopByUserId(String userId);

}

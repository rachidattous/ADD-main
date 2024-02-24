package com.add.file.controllers;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.add.file.dto.UserPhotoDTO;
import com.add.file.model.UserPhoto;
import com.add.file.services.UserPhotoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file/userPhoto")
public class UserPhotoController {

  private final UserPhotoService userPhotoService;

  @PutMapping(value = "/{userId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public Optional<UserPhoto> uploadUserPhoto(@ModelAttribute UserPhotoDTO userPhotoDTO, @PathVariable String userId) {
    return userPhotoService.uploadUserPhoto(userPhotoDTO, userId);

  }

  @GetMapping("/{userId}")
  public Optional<UserPhoto> getUserPhoto(@PathVariable String userId) {
    return userPhotoService.getByUserId(userId);

  }

  @DeleteMapping("/{userId}")
  public void deleteUserPhoto(@PathVariable String userId) {
    userPhotoService.deleteUserPhoto(userId);

  }

  @GetMapping("/download/{userId}")
  public ResponseEntity<byte[]> downloadUserPhoto(@PathVariable String userId) {
    return userPhotoService.downloadUserPhoto(userId);

  }

}

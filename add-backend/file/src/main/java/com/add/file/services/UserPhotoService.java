package com.add.file.services;

import com.add.file.dto.UserPhotoDTO;

import com.add.file.exception.photo.PhotoNotFoundException;
import com.add.file.model.UserPhoto;

import com.add.file.repository.UserPhotoRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPhotoService {

  private final UserPhotoRepository userPhotoRepository;

  private final FileService fileService;

  public void deleteUserPhoto(String userId) {

    getByUserId(userId)
        .ifPresent(fileService::deleteFile);

  }

  public Optional<UserPhoto> getByUserId(String userId) {

    return userPhotoRepository.findTopByUserId(userId);

  }

  public Optional<UserPhoto> uploadUserPhoto(UserPhotoDTO userPhotoDTO, String userId) {
    deleteUserPhoto(userId);
    UserPhoto file = new UserPhoto();
    fileService.uploadFile(userPhotoDTO.getFile(), file);
    file.setUserId(userId);
    return Optional.of(userPhotoRepository.save(file));

  }

  public ResponseEntity<byte[]> downloadUserPhoto(String userId) {
    return getByUserId(userId)
        .map(fileService::downloadFile)
        .orElseThrow(PhotoNotFoundException::new);

  }

}

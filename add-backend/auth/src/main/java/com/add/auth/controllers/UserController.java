package com.add.auth.controllers;

import com.add.auth.dto.UserInput;
import com.add.auth.dto.UserUpdate;
import com.add.auth.dto.VerifyEmailDTO;
import com.add.auth.services.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.add.auth.model.UserData;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

import com.add.auth.dto.AFileDTO;
import com.add.auth.dto.ChangePasswordDTO;
import com.add.auth.dto.ResetPasswordDTO;
import com.add.auth.dto.SearchUserDTO;

@RestController
@RequestMapping("/api/auth/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public Optional<UserData> createUser(@RequestBody UserInput userInput) {
    return userService.createUser(userInput);
  }

  @PostMapping("/search")
  public Page<UserData> search(@RequestBody SearchUserDTO searchUserData, Pageable pageable) {
    return userService.searchUserData(searchUserData, pageable);
  }

  @PostMapping("/verifyEmail")
  public Optional<UserData> verify(@RequestBody VerifyEmailDTO verifyEmailDTO) {
    return userService.verifyEmail(verifyEmailDTO);
  }

  @PatchMapping("/forgetPasswordValidator/{code}")
  public Optional<UserData> forgotPasswordValidator(@PathVariable String code,
      @RequestBody ResetPasswordDTO resetPasswordDTO) {
    return userService.forgotPasswordValidator(code, resetPasswordDTO);
  }

  @PostMapping("/forgetPassword/{username}")
  public void forgotPasswordValidator(@PathVariable String username) {
    userService.forgotPassword(username);
  }

  @PostMapping("/changePassword/{userId}")
  public void changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO,
      @PathVariable String userId) {
    userService.changePassword(changePasswordDTO, userId);
  }

  @GetMapping("/{userId}")
  public Optional<UserData> getUserById(@PathVariable String userId) {
    return userService.getUserById(userId);
  }

  @GetMapping("/export/xls")
  public ResponseEntity<byte[]> export() {
    return userService.export();
  }

  @PutMapping(value = "/import/xls", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public List<UserData> importUsers(@ModelAttribute AFileDTO fileDTO) {
    return userService.importUsers(fileDTO);
  }

  @GetMapping("/current")
  public Optional<UserData> getCurrentUser() {
    return userService.getCurrentUser();
  }

  @PatchMapping("/{userId}")
  public Optional<UserData> updateUser(@PathVariable String userId, @RequestBody UserUpdate userInput) {
    return userService.updateUser(userId, userInput);
  }

  @DeleteMapping("/{userId}")
  public void deleteUser(@PathVariable String userId) {
    userService.deleteUser(userId);
  }

  @PostMapping("/{userId}/grant/{roleId}")
  public Optional<UserData> grantRole(@PathVariable String userId, @PathVariable String roleId) {
    return userService.grantRole(userId, roleId);
  }

  @DeleteMapping("/{userId}/revoke/{roleId}")
  public Optional<UserData> revokeRole(@PathVariable String userId, @PathVariable String roleId) {
    return userService.revokeRole(userId, roleId);
  }

  @PatchMapping("/{userId}/resetPasswordFirstTime")
  public Optional<UserData> resetPasswordFirstTime(@PathVariable String userId,
      @Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
    return userService.resetPassword(userId, resetPasswordDTO);
  }

  @GetMapping("/active/id")
  public List<String> activeUsersId() {
    return userService.getActiveUsersId();
  }

  @GetMapping("group/{groupName}")
  public List<String> usersIdByGroupName(@PathVariable String groupName) {
    return userService.getUsersIdByGroupName();
  }

}

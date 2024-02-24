package com.add.auth.services;

import com.add.auth.dto.UserInput;
import com.add.auth.dto.UserUpdate;
import com.add.auth.dto.VerifyEmailDTO;

import com.add.auth.exception.role.RoleNotFoundException;
import com.add.auth.exception.role.RoleNullException;
import com.add.auth.exception.user.PassworNullException;
import com.add.auth.exception.user.PasswordChangeException;
import com.add.auth.exception.user.PasswordConfirmException;
import com.add.auth.exception.user.PasswordWeakException;
import com.add.auth.exception.user.PasswordWrongException;
import com.add.auth.exception.user.UserAlreadyExistsException;
import com.add.auth.exception.user.UserCreationException;
import com.add.auth.exception.user.UserCurrentException;
import com.add.auth.exception.user.UserNotFoundException;
import com.add.auth.exception.user.UserNullException;
import com.add.auth.exception.user.UserUpdateException;
import com.add.auth.model.Role;

import com.add.auth.specification.UserDataSpecification;
import com.add.auth.util.DateUtility;

import com.add.auth.util.Utils;

import org.springframework.stereotype.Service;

import com.add.auth.model.User;
import com.add.auth.model.UserData;

import java.util.Set;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.OAuth2Constants;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessToken.Access;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import org.apache.commons.lang.StringUtils;

import com.add.auth.repository.UserDataRepository;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;

import com.add.auth.constants.Constants;
import com.add.auth.constants.Gender;
import com.add.auth.constants.PasswordStrength;
import com.add.auth.constants.UserStatus;
import com.add.auth.dto.AFileDTO;
import com.add.auth.dto.ChangePasswordDTO;

import com.add.auth.dto.ResetPasswordDTO;
import com.add.auth.dto.RoleModelRepresentation;
import com.add.auth.dto.SearchUserDTO;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  @Value("${keycloak.credentials.secret}")
  private String secretKey;

  @Value("${keycloak.resource}")
  private String clientId;

  @Value("${keycloak.auth-server-url}")
  private String authUrl;

  @Value("${keycloak.realm}")
  private String realm;

  private final Keycloak keycloak;

  private final KeycloakSecurityContext keycloakSecurityContext;

  private final RoleService roleService;

  private final ModelMapper modelMapper;

  private final UserDataRepository userDataRepository;

  private final ExportService exportService;

  private final ImportService importService;

  private final VerifyEmailService verifyEmailService;

  private final ForgotPasswordService forgotPasswordService;

  public CredentialRepresentation kcPassword(String password, boolean temporary) {
    if (password == null) {
      throw new PassworNullException();
    }
    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
    credentialRepresentation.setTemporary(temporary);
    credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
    credentialRepresentation.setValue(password);
    return credentialRepresentation;

  }

  public UserRepresentation prepareUserRepresentation(User user) {
    UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setEnabled(true);
    userRepresentation.setUsername(user.getUsername());
    userRepresentation.setFirstName(StringUtils.capitalize(user.getFirstName()));
    userRepresentation.setLastName(user.getLastName().toUpperCase());
    userRepresentation.setEmail(user.getEmail());
    userRepresentation.setEmailVerified(user.isEmailVerified());
    userRepresentation.setRealmRoles(user.getRoles());
    userRepresentation.setCredentials(Collections.singletonList(kcPassword(user.getPassword(), false)));
    return userRepresentation;
  }

  public Optional<UserData> createAdminUser() {

    UserInput userInput = UserInput.builder()
        .birthDate(DateUtility.nowDate())
        .joinedDate(DateUtility.nowDate())
        .username("ADMIN")
        .email("yassineaitmalek7@gmail.com")
        .firstName("Admin")
        .lastName("ADMIN")
        .gender(Gender.MALE)
        .rolesName(Arrays.asList("SUPER_ADMIN"))
        .build();
    User user = modelMapper.map(userInput, User.class);
    user.setEmailVerified(true);
    user.setRoles(userInput.getRolesName());
    if (!PasswordStrength.getStrength(Constants.USER_DEFAULT_PASSWORD).isStrong()) {
      throw new PasswordWeakException();

    }
    user.setPassword(Constants.USER_DEFAULT_PASSWORD);

    if (userDataRepository.existsByUsername(user.getUsername())) {
      throw new UserAlreadyExistsException();
    }
    String userId = Optional.of(getUsersResources().create(prepareUserRepresentation(user)))
        .filter(response -> response.getStatusInfo().equals(Response.Status.CREATED))
        .map(response -> response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1"))
        .orElseThrow(UserCreationException::new);

    List<Role> userRoles = roleService.stringToRoles(userInput.getRolesName());

    UserData userData = modelMapper.map(userInput, UserData.class);
    userData.setId(userId);
    userData.setUserStatus(UserStatus.NEW);
    userData.setJoinedDate(DateUtility.nowDate());
    userData.setRoles(userRoles);
    userDataRepository.save(userData);
    log.info("new admin is created with id {}", userId);
    return Optional.of(userData);

  }

  public Optional<UserData> createUser(UserInput userInput) {

    if (userDataRepository.existsByUsername(userInput.getUsername())) {
      throw new UserAlreadyExistsException();
    }
    User user = modelMapper.map(userInput, User.class);
    user.setRoles(userInput.getRolesName());
    if (!PasswordStrength.getStrength(Constants.USER_DEFAULT_PASSWORD).isStrong()) {
      throw new PasswordWeakException();

    }

    user.setPassword(Constants.USER_DEFAULT_PASSWORD);

    String userId = Optional.of(getUsersResources().create(prepareUserRepresentation(user)))
        .filter(response -> response.getStatusInfo().equals(Response.Status.CREATED))
        .map(response -> response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1"))
        .orElseThrow(UserCreationException::new);

    List<Role> userRoles = roleService.stringToRoles(userInput.getRolesName());

    UserData userData = modelMapper.map(userInput, UserData.class);
    userData.setId(userId);
    userData.setUserStatus(UserStatus.NEW);
    userData.setJoinedDate(DateUtility.nowDate());
    userData.setRoles(userRoles);
    userDataRepository.save(userData);

    log.info("sending email verification to user with id {}", userId);
    verifyEmailService.sendVerificationEmail(userData);
    log.info("new user is created with id {}", userId);
    return Optional.of(userData);

  }

  public UsersResource getUsersResources() {
    return keycloak.realm(realm).users();
  }

  public Optional<UserResource> getUserResource(String userId) {
    return Optional.of(getUsersResources().get(userId));
  }

  public List<UserRepresentation> getAllUsers(Integer firstResult, Integer maxResults) {
    return getUsersResources().list(firstResult, maxResults);
  }

  public Optional<UserRepresentation> getUserRepresentationByUserId(String userId) {

    return Optional.ofNullable(userId)
        .map(this::getUserResource)
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .map(e -> Optional.of(e.toRepresentation()))
        .orElseThrow(UserNotFoundException::new);

  }

  public Optional<UserData> getUserById(String userId) {

    return userDataRepository.findById(userId);

  }

  public Optional<UserData> grantRole(String userId, String roleId) {
    RoleModelRepresentation role = roleService.getRoleRepresenationById(roleId)
        .orElseThrow(RoleNotFoundException::new);
    getUserResource(userId).ifPresent(e -> assignRole(e, role.getRepresentation()));
    return userDataRepository.findById(userId)
        .map(e -> {
          e.setRoles(Utils.addToList(e.getRoles(), role.getModel()));
          return Optional.of(userDataRepository.save(e));
        })
        .orElse(Optional.empty());

  }

  public Optional<UserData> revokeRole(String userId, String roleId) {
    RoleModelRepresentation role = roleService.getRoleRepresenationById(roleId)
        .orElseThrow(RoleNotFoundException::new);
    getUserResource(userId).ifPresent(e -> revokeRole(e, role.getRepresentation()));
    return userDataRepository.findById(userId)
        .map(e -> {
          e.setRoles(Utils.removeFromList(e.getRoles(), role.getModel()));
          return Optional.of(userDataRepository.save(e));
        })
        .orElse(Optional.empty());

  }

  public Optional<UserResource> assignRole(UserResource userResource, RoleRepresentation roleRepresentation) {
    return assignRoles(userResource, Arrays.asList(roleRepresentation));
  }

  public Optional<UserResource> revokeRole(UserResource userResource, RoleRepresentation roleRepresentation) {
    return revokeRoles(userResource, Arrays.asList(roleRepresentation));
  }

  public Optional<UserResource> assignRoles(UserResource userResource, List<RoleRepresentation> roleRepresentations) {
    if (!Utils.notNullandEmpty(roleRepresentations)) {
      throw new RoleNullException();
    }
    userResource.roles().realmLevel().add(roleRepresentations);
    return Optional.of(userResource);

  }

  public Optional<UserResource> resetRoles(UserResource userResource, List<RoleRepresentation> newRoles) {

    List<RoleRepresentation> currentRoles = roleService.getByUserId(userResource.toRepresentation().getId());
    if (!currentRoles.isEmpty()) {
      userResource.roles().realmLevel().remove(currentRoles);
    }
    if (!Utils.notNullandEmpty(newRoles)) {
      throw new RoleNullException();
    }
    userResource.roles().realmLevel().add(newRoles);
    return Optional.of(userResource);

  }

  public Optional<UserResource> revokeRoles(UserResource userResource, List<RoleRepresentation> roleRepresentations) {

    if (!Utils.notNullandEmpty(roleRepresentations)) {
      throw new RoleNullException();
    }
    userResource.roles().realmLevel().remove(roleRepresentations);
    return Optional.of(userResource);

  }

  public Optional<UserData> resetPassword(String userId, ResetPasswordDTO resetPassword) {

    return getUserResource(userId)
        .map(e -> resetPassword(e, resetPassword))
        .orElse(Optional.empty());

  }

  public Optional<UserData> resetPassword(UserResource userResource, ResetPasswordDTO resetPassword) {
    if (!resetPassword.isValid()) {
      throw new PasswordConfirmException();
    }
    if (!PasswordStrength.getStrength(resetPassword.getPassword()).isStrong()) {
      throw new PasswordWeakException();

    }
    UserData userData = userDataRepository.findById(userResource.toRepresentation().getId())
        .orElseThrow(UserNotFoundException::new);
    userResource.resetPassword(kcPassword(resetPassword.getPassword(), false));
    userData.setUserStatus(UserStatus.ACTIVE);

    return Optional.of(userDataRepository.save(userData));

  }

  public boolean checkOldPasword(String username, String password) {
    try {
      KeycloakBuilder.builder()
          .realm(realm)
          .clientId(clientId)
          .serverUrl(authUrl)
          .grantType(OAuth2Constants.PASSWORD)
          .clientSecret(secretKey)
          .username(username)
          .password(password)
          .build()
          .tokenManager()
          .getAccessToken();
      return true;
    } catch (Exception e) {
      return false;
    }

  }

  public void changePassword(ChangePasswordDTO changePasswordDTO, String userId) {
    try {
      log.info("changing password for user with id {}", userId);
      UserData user = getUserById(userId).orElseThrow(UserNotFoundException::new);
      if (!checkOldPasword(user.getUsername(), changePasswordDTO.getOldPassword())) {
        throw new PasswordWrongException();
      }
      resetPassword(userId, ResetPasswordDTO.builder().password(changePasswordDTO.getNewPassword())
          .confirmPassword(changePasswordDTO.getConfirmPassword()).build());

    } catch (Exception e) {
      log.info(e.getMessage(), e);
      throw new PasswordChangeException();
    }
  }

  public void deleteUser(String userId) {
    Optional.of(userId)
        .map(userDataRepository::findById)
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .ifPresent(this::deleteUser);

  }

  public void deleteUser(UserData user) {
    if (user == null) {
      throw new UserNullException();
    }
    userDataRepository.delete(user);
    getUsersResources().delete(user.getId());

  }

  public Optional<AccessToken> getAccessToken() {
    return Optional.of(keycloakSecurityContext.getToken());

  }

  public Set<String> getCurrentUserRoles() {

    return getAccessToken()
        .map(AccessToken::getRealmAccess)
        .map(Access::getRoles)
        .orElse(new HashSet<>());

  }

  public Optional<String> getCurrentUserId() {

    try {
      return getAccessToken().map(token -> Optional.of(token.getSubject())).orElse(Optional.empty());
    } catch (Exception e) {
      log.error(e.getMessage());
      return Optional.empty();
    }

  }

  public Optional<UserData> getCurrentUser() {
    try {
      return getCurrentUserId()
          .map(userDataRepository::findById)
          .orElse(Optional.empty());

    } catch (Exception e) {
      log.error(e.getMessage());
      throw new UserCurrentException();
    }

  }

  public UserRepresentation resetCredentials(String userId, String password) {
    UserResource userResource = keycloak.realm(realm).users().get(userId);
    userResource.credentials()
        .stream()
        .filter(credentialRepresentation -> credentialRepresentation.getType().equals("otp"))
        .findFirst()
        .ifPresent(credentialRepresentation -> userResource.removeCredential(credentialRepresentation.getId()));
    CredentialRepresentation credentialRepresentation = kcPassword(password, true);
    userResource.resetPassword(credentialRepresentation);
    return userResource.toRepresentation();
  }

  public Optional<UserRepresentation> updateUserRepresentation(String userId, UserUpdate userInput) {

    return getUserResource(userId)
        .map(userResource -> {
          UserRepresentation userRepresentation = new UserRepresentation();
          userRepresentation.setFirstName(userInput.getFirstName());
          userRepresentation.setLastName(userInput.getLastName());
          userRepresentation.setEmail(userInput.getEmail());

          userResource.update(userRepresentation);
          resetRoles(userResource, roleService.getByRoles(roleService.stringToRoles(userInput.getRolesName())));
          return Optional.of(userResource.toRepresentation());

        })

        .orElse(Optional.empty());

  }

  public Optional<UserData> verifyEmail(VerifyEmailDTO verifyEmailDTO) {
    return verifyEmail(verifyEmailDTO.getCode());
  }

  public Optional<UserData> verifyEmail(String code) {

    String userId = verifyEmailService.verifyEmail(code);
    UserData userData = getUserById(userId).orElseThrow(UserNotFoundException::new);
    userData.setEmailVerified(true);

    getUserResource(userId)
        .ifPresent(userResource -> {
          UserRepresentation userRepresentation = new UserRepresentation();
          userRepresentation.setFirstName(userData.getFirstName());
          userRepresentation.setLastName(userData.getLastName());
          userRepresentation.setEmail(userData.getEmail());
          userRepresentation.setEmailVerified(true);
          userResource.update(userRepresentation);
          resetRoles(userResource, roleService.getByRoles(userData.getRoles()));
          userResource.toRepresentation();

        });

    return Optional.of(userDataRepository.save(userData));
  }

  public Optional<UserData> forgotPasswordValidator(String code, ResetPasswordDTO resetPasswordDTO) {

    String userId = forgotPasswordService.forgotPassword(code);
    return resetPassword(userId, resetPasswordDTO);
  }

  public void forgotPassword(String username) {

    userDataRepository.findByUsername(username)
        .ifPresent(forgotPasswordService::sendForgotPasswordEmail);
  }

  public Optional<UserData> updateUser(String userId, UserUpdate userInput) {

    updateUserRepresentation(userId, userInput);
    UserData userData = updateUserData(userId, userInput)
        .map(e -> e)
        .orElseThrow(UserUpdateException::new);
    return Optional.of(userData);
  }

  public Optional<UserData> updateUserData(String userId, UserUpdate userInput) {
    return Optional.of(userId)
        .map(userDataRepository::findById)
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .map(e -> updateUserData(e, userInput))
        .orElse(Optional.empty());

  }

  public Optional<UserData> updateUserData(UserData userData, UserUpdate userInput) {
    return Optional.of(userData)
        .map(e -> {
          modelMapper.map(userInput, e);
          if (userInput.getRolesName() != null) {

            userData.setRoles(roleService.stringToRoles(userInput.getRolesName()));
          }

          return Optional.of(userDataRepository.save(e));
        })
        .orElse(Optional.empty());

  }

  public Page<UserData> searchUserData(SearchUserDTO searchUser, Pageable pageable) {
    return userDataRepository.findAll(UserDataSpecification.searchRequest(searchUser), pageable);

  }

  public List<UserData> getAllUserData() {
    return userDataRepository.findAll();
  }

  public ResponseEntity<byte[]> export() {
    return exportService.exportUsersExcel(getAllUserData());
  }

  public Optional<UserData> createOrUpdate(UserInput userInput) {

    String userId = userDataRepository.findByUsername(userInput.getUsername()).map(e -> e.getId()).orElse(null);

    if (userId == null) {

      return createUser(userInput);
    } else {

      return updateUser(userId, modelMapper.map(userInput, UserUpdate.class));
    }
  }

  public List<UserData> importUsers(AFileDTO fileDTO) {
    return importService.importUsers(fileDTO)
        .stream()
        .map(this::createOrUpdate)
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .collect(Collectors.toList());

  }

  public List<String> getActiveUsersId() {
    return userDataRepository.findAll()
        .stream()
        .map(e -> e.getId())
        .collect(Collectors.toList());
  }

  public List<String> getUsersIdByGroupName() {
    return userDataRepository.findAll()
        .stream()
        .map(e -> e.getId())
        .collect(Collectors.toList());
  }

}

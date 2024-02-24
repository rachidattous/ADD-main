package com.add.auth.specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.jpa.domain.Specification;

import com.add.auth.constants.Gender;
import com.add.auth.dto.SearchUserDTO;
import com.add.auth.model.UserData;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserDataSpecification {

  private String like(String element) {
    return Optional.ofNullable(element).map(e -> "%" + e + "%").orElse("");
  }

  public <T, U> Optional<Specification<U>> transformer(T object, Function<T, Specification<U>> mapper) {
    return Optional.ofNullable(object)
        .map(e -> Optional.ofNullable(mapper.apply(e)))
        .orElse(Optional.empty());
  }

  private Specification<UserData> distinct() {
    return (root, query, builder) -> {
      query.distinct(true);
      return builder.isNotNull(root.get("id"));
    };
  }

  private Specification<UserData> hasBirthDate(LocalDate birthDate) {
    return (root, query, builder) -> builder.equal(root.get("birthDate"), birthDate);

  }

  private Specification<UserData> hasJoinedDate(LocalDate joinedDate) {
    return (root, query, builder) -> builder.equal(root.get("joinedDate"), joinedDate);

  }

  private Specification<UserData> hasGender(Gender gender) {
    return (root, query, builder) -> builder.equal(root.get("gender"), gender);

  }

  private Specification<UserData> hasFirstName(String firstName) {
    return (root, query, builder) -> builder.like(root.get("firstName"), like(firstName));

  }

  private Specification<UserData> hasLastName(String lastName) {
    return (root, query, builder) -> builder.like(root.get("lastName"), like(lastName));

  }

  private Specification<UserData> hasUserName(String username) {
    return (root, query, builder) -> builder.like(root.get("username"), like(username));

  }

  private Specification<UserData> hasEmail(String email) {
    return (root, query, builder) -> builder.like(root.get("email"), like(email));

  }

  private Specification<UserData> hasRole(String roleName) {
    return (root, query, builder) -> builder.like(root.join("roles").get("name"), like(roleName));

  }

  public Specification<UserData> searchRequest(SearchUserDTO searchUserDTO) {

    return Arrays.asList(
        transformer(searchUserDTO.getJoinedDate(), UserDataSpecification::hasJoinedDate),
        transformer(searchUserDTO.getGender(), UserDataSpecification::hasGender),
        transformer(searchUserDTO.getFirstName(), UserDataSpecification::hasFirstName),
        transformer(searchUserDTO.getLastName(), UserDataSpecification::hasLastName),
        transformer(searchUserDTO.getUsername(), UserDataSpecification::hasUserName),
        transformer(searchUserDTO.getEmail(), UserDataSpecification::hasEmail),
        transformer(searchUserDTO.getBirthDate(), UserDataSpecification::hasBirthDate),
        transformer(searchUserDTO.getRoleName(), UserDataSpecification::hasRole))
        .stream()
        .filter(Optional::isPresent)
        .map(Optional::get)
        .reduce(Specification.where(distinct()), Specification::and);

  }

}

package com.add.auth.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.add.auth.constants.PasswordStrength;
import com.add.auth.dto.ChangePasswordDTO;
import com.add.auth.validation.ChangePasswordValidator;

import java.util.Objects;

public class ChangePasswordValidatorImpl implements ConstraintValidator<ChangePasswordValidator, ChangePasswordDTO> {

	@Override
	public boolean isValid(ChangePasswordDTO element, ConstraintValidatorContext context) {
		if (Objects.isNull(element)) {
			return false;
		}
		if (Objects.isNull(element.getOldPassword()) || element.getOldPassword().isEmpty()) {
			return false;
		}
		if (Objects.isNull(element.getNewPassword()) || element.getNewPassword().isEmpty()) {
			return false;
		}

		if (Objects.isNull(element.getConfirmPassword()) || element.getConfirmPassword().isEmpty()) {
			return false;
		}

		if (!element.getConfirmPassword().equals(element.getNewPassword())) {
			return false;
		}

		if (PasswordStrength.getStrength(element.getNewPassword()).isWeak()) {
			return false;
		}

		return true;
	}
}
package com.add.auth.constants;

public enum PasswordStrength {

  WEAK,

  MEDIUM,

  STRONG;

  private static final int MIN_STRENGTH = 6;

  private static final int MAX_STRENGTH = 8;

  private static final int MIN_LENGTH = 8;

  private static final int MAX_LENGTH = 10;

  public boolean isStrong() {
    return MEDIUM.equals(this) || STRONG.equals(this);
  }

  public boolean isWeak() {
    return WEAK.equals(this);
  }

  public static int calculatePasswordStrength(String password) {

    if (passwordLength(password) == 0) {
      return 0;
    }
    return passwordLength(password) + containsDigits(password) + containsLowerCaseLetter(password)
        + containsUpperCaseLetter(password) + containsSpecialCharacter(password);
  }

  private static int passwordLength(String password) {
    if (password.length() < MIN_LENGTH) {
      return 0;
    }

    else if (password.length() >= MIN_LENGTH && password.length() < MAX_LENGTH) {
      return 1;
    }

    else {
      return 2;
    }

  }

  private static int containsDigits(String password) {
    if (!password.matches("(?=.*[0-9]).*")) {
      return 0;
    }
    return 2;
  }

  private static int containsLowerCaseLetter(String password) {
    if (!password.matches("(?=.*[a-z]).*")) {
      return 0;
    }
    return 2;
  }

  private static int containsUpperCaseLetter(String password) {
    if (!password.matches("(?=.*[A-Z]).*")) {
      return 0;
    }
    return 2;
  }

  private static int containsSpecialCharacter(String password) {
    if (!password.matches("(?=.*[~!@#$%^&*()_-]).*")) {
      return 0;
    }
    return 2;
  }

  public static PasswordStrength getStrengthByScore(int score) {
    if (score < MIN_STRENGTH) {
      return WEAK;
    } else if (score >= MIN_STRENGTH && score <= MAX_STRENGTH) {
      return MEDIUM;
    } else {
      return STRONG;
    }
  }

  public static PasswordStrength getStrength(String password) {
    return getStrengthByScore(calculatePasswordStrength(password));
  }

}

package org.kamsystem.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.kamsystem.common.enums.UserRole;

@Getter
@AllArgsConstructor
public class UserLoginResponse {
  private String token;
  private Long userId;
  private String name;
  private String mobile;
  private String email;
  private UserRole role;
}

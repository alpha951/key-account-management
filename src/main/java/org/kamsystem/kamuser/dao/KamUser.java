package org.kamsystem.kamuser.dao;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.kamsystem.common.enums.UserRole;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KamUser {

    private Long id;

    @NonNull
    private String mobile;

    @NonNull
    private String name;

    @NonNull
    private String password;

    @NonNull
    private UserRole role;
    private String employeeId;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;
    private Boolean isActive;

    public KamUser(@NonNull String mobile, @NonNull String name, @NonNull String password,
        @NonNull UserRole role, String employeeId, String email,
        Boolean isActive) {
        this.mobile = mobile;
        this.name = name;
        this.password = password;
        this.role = role;
        this.employeeId = employeeId;
        this.email = email;
        this.isActive = isActive;
    }
}
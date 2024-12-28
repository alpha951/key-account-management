package org.kamsystem.kamuser.dao;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(min = 10, max = 13)
    private String mobile;

    @NonNull
    @Size(min = 2, max = 50)
    private String name;

    @NonNull
    @Size(min = 6, max = 20)
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
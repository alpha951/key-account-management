package org.kamsystem.kamuser.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.kamsystem.common.enums.UserRole;

@AllArgsConstructor
@Getter
public class KamUserUpdateModel {

    @NonNull
    @Size(min = 10, max = 13)
    private String mobile;
    @NonNull
    private UserRole role;
    @NonNull
    private Boolean isActive;
}
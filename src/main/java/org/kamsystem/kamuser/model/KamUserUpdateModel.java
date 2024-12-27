package org.kamsystem.kamuser.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.kamsystem.common.enums.UserRole;

@AllArgsConstructor
@Getter
public class KamUserUpdateModel {

    @NonNull
    private String mobile;
    @NonNull
    private UserRole role;
    @NonNull
    private Boolean isActive;
}
package org.kamsystem.kamuser.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.kamsystem.common.enums.UserRole;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KamUserUpdateModel {

    @NonNull
    @Size(min = 10, max = 13)
    private String mobile;
    @NonNull
    private UserRole role;
    @NonNull
    private Boolean isActive;
}
package org.kamsystem.authentication.model;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class UserLoginRequest {

    @NonNull
    @Size(min = 10, max = 13)
    private String mobile;

    @NonNull
    @Size(min=6, max=20)
    private String password;
}

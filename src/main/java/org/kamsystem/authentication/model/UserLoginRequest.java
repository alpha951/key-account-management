package org.kamsystem.authentication.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class UserLoginRequest {

    @NonNull
    private String mobile;

    @NonNull
    private String password;
}

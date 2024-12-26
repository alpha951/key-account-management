package org.kamsystem.kamuser.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.kamsystem.authentication.annotation.UserAuth;
import org.kamsystem.common.enums.UserRole;
import org.kamsystem.common.model.ApiResponse;
import org.kamsystem.kamuser.dao.KamUser;
import org.kamsystem.kamuser.model.KamUserUpdateModel;
import org.kamsystem.kamuser.service.IKamUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/kam-user")
public class KamUserController {

    private final IKamUserService kamUserService;

    @UserAuth(allowedFor = UserRole.SUPER_ADMIN)
    @PostMapping("/create-user")
    public ResponseEntity<?> createKamUser(@RequestBody @Valid KamUser kamUser, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new ApiResponse<>(false, result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        kamUserService.createUser(kamUser);
        return ResponseEntity.ok().build();
    }

    @UserAuth(allowedFor = UserRole.SUPER_ADMIN)
    @PostMapping("/update-role")
    public ResponseEntity<?> updateKamUserRole(@RequestBody @Valid KamUserUpdateModel kamUserUpdateModel,
        BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new ApiResponse<>(false, result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        kamUserService.updateUserRole(kamUserUpdateModel);
        return ResponseEntity.ok().build();
    }
}

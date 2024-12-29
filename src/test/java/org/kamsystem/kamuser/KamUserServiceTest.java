package org.kamsystem.kamuser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kamsystem.authentication.utils.PasswordUtil;
import org.kamsystem.common.enums.UserRole;
import org.kamsystem.kamuser.dao.KamUser;
import org.kamsystem.kamuser.model.KamUserUpdateModel;
import org.kamsystem.kamuser.repository.IKamUserRepository;
import org.kamsystem.kamuser.service.KamUserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

@ExtendWith(MockitoExtension.class)
class KamUserServiceTest {

    @Mock
    private IKamUserRepository kamUserRepository;

    @InjectMocks
    private KamUserService kamUserService;

    @Mock
    private PasswordUtil passwordUtil;

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() {
        // Given
        String rawPassword = "password123";
        String mockedHash = "$2a$10$MockedHashValueForTesting";

        KamUser kamUser = new KamUser(
            "9876543210",
            "Test User",
            rawPassword,
            UserRole.KEY_ACCOUNT_MANAGER,
            "EMP123",
            "test@example.com",
            true
        );

        when(passwordUtil.hashPassword(rawPassword)).thenReturn(mockedHash);

        doNothing().when(kamUserRepository).createUser(
            kamUser.getMobile(),
            kamUser.getName(),
            mockedHash,
            kamUser.getRole().getId(),
            kamUser.getEmployeeId(),
            kamUser.getEmail(),
            kamUser.getIsActive()
        );

        // When
        assertDoesNotThrow(() -> kamUserService.createUser(kamUser));

        // Then
        verify(kamUserRepository).createUser(
            kamUser.getMobile(),
            kamUser.getName(),
            mockedHash,
            kamUser.getRole().getId(),
            kamUser.getEmployeeId(),
            kamUser.getEmail(),
            kamUser.getIsActive()
        );
    }

    @Test
    @DisplayName("Should throw exception when user creation fails")
    void shouldThrowExceptionWhenUserCreationFails() {
        // Given
        String rawPassword = "password123";
        String mockedHash = "$2a$10$MockedHashValueForTesting";

        KamUser kamUser = new KamUser(
            "9876543210",
            "Test User",
            rawPassword,
            UserRole.KEY_ACCOUNT_MANAGER,
            "EMP123",
            "test@example.com",
            true
        );

        when(passwordUtil.hashPassword(rawPassword)).thenReturn(mockedHash);

        // Simulate a database error
        doThrow(new DataAccessException("User creation failed") {
        }).when(kamUserRepository).createUser(
            kamUser.getMobile(),
            kamUser.getName(),
            mockedHash,  // Use mocked hash instead of raw password
            kamUser.getRole().getId(),
            kamUser.getEmployeeId(),
            kamUser.getEmail(),
            kamUser.getIsActive()
        );

        // When & Then
        assertThrows(DataAccessException.class, () -> kamUserService.createUser(kamUser));
    }

    @Test
    @DisplayName("Should update user role successfully")
    void shouldUpdateUserRoleSuccessfully() {
        // Given
        KamUserUpdateModel updateModel = new KamUserUpdateModel();
        updateModel.setMobile("9876543210");
        updateModel.setRole(UserRole.KEY_ACCOUNT_MANAGER);
        updateModel.setIsActive(true);

        doNothing().when(kamUserRepository).updateUserRole(
            updateModel.getMobile(), updateModel.getRole().getId(), updateModel.getIsActive()
        );

        // When
        assertDoesNotThrow(() -> kamUserService.updateUserRole(updateModel));

        // Then
        verify(kamUserRepository).updateUserRole(
            updateModel.getMobile(), updateModel.getRole().getId(), updateModel.getIsActive()
        );
    }
}
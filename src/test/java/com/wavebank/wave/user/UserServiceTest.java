package com.wavebank.wave.user;

import com.wavebank.wave.exception.UserAlreadyExistsException;
import com.wavebank.wave.registration.RegistrationRequest;
import com.wavebank.wave.registration.token.VerificationToken;
import com.wavebank.wave.registration.token.VerificationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private VerificationTokenRepository tokenRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsers() {
        // Arrange
        User user1 = new User(1L, "John", "Doe", "john.doe@example.com", "password", "USER", true);
        User user2 = new User(2L, "Jane", "Smith", "jane.smith@example.com", "password", "USER", true);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<User> users = userService.getUsers();

        // Assert
        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testRegisterUserSuccess() {
        // Arrange
        RegistrationRequest request = new RegistrationRequest("John", "Doe", "john.doe@example.com", "password", "USER");
        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
        User savedUser = new User(null, "John", "Doe", "john.doe@example.com", "encodedPassword", "USER", false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User registeredUser = userService.registerUser(request);

        // Assert
        assertNotNull(registeredUser);
        assertEquals("john.doe@example.com", registeredUser.getEmail());
        verify(userRepository, times(1)).findByEmail(request.email());
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(request.password());
    }

    @Test
    void testRegisterUserThrowsExceptionIfUserAlreadyExists() {
        // Arrange
        RegistrationRequest request = new RegistrationRequest("John", "Doe", "john.doe@example.com", "password", "USER");
        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(request));
        verify(userRepository, times(1)).findByEmail(request.email());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testFindByEmail() {
        // Arrange
        String email = "john.doe@example.com";
        User user = new User(1L, "John", "Doe", email, "password", "USER", true);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.findByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testSaveUserVerificationToken() {
        // Arrange
        User user = new User(1L, "John", "Doe", "john.doe@example.com", "password", "USER", false);
        String token = "verificationToken";

        // Act
        userService.saveUserVerificationToken(user, token);

        // Assert
        verify(tokenRepository, times(1)).save(any(VerificationToken.class));
    }

    @Test
    void testValidateTokenSuccess() {
        // Arrange
        String token = "validToken";
        User user = new User(1L, "John", "Doe", "john.doe@example.com", "password", "USER", false);
        VerificationToken verificationToken = new VerificationToken(token, user);
        when(tokenRepository.findByToken(token)).thenReturn(verificationToken);

        // Act
        String result = userService.validateToken(token);

        // Assert
        assertEquals("valid", result);
        assertTrue(user.isEnabled());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testValidateTokenThrowsExceptionIfTokenIsNull() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> userService.validateToken(null));
    }

    @Test
    void testDeleteTokenSuccess() {
        // Arrange
        String token = "validToken";
        VerificationToken verificationToken = new VerificationToken(token, new User());
        when(tokenRepository.findByToken(token)).thenReturn(verificationToken);

        // Act
        String result = userService.deleteToken(token);

        // Assert
        assertEquals("Token deleted", result);
        verify(tokenRepository, times(1)).delete(verificationToken);
    }

    @Test
    void testDeleteTokenNotFound() {
        // Arrange
        String token = "invalidToken";
        when(tokenRepository.findByToken(token)).thenReturn(null);

        // Act
        String result = userService.deleteToken(token);

        // Assert
        assertEquals("Token not found", result);
        verify(tokenRepository, never()).delete(any(VerificationToken.class));
    }
}
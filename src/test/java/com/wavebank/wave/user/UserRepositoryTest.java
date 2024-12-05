package com.wavebank.wave.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john.doe@example.com");
        testUser.setPassword("password");
        testUser.setRole("USER");
        testUser.setEnabled(true);

        userRepository.save(testUser);
    }

    @Test
    void testFindByEmail_UserExists() {
        // Act
        Optional<User> foundUser = userRepository.findByEmail("john.doe@example.com");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getEmail(), foundUser.get().getEmail());
    }

    @Test
    void testFindByEmail_UserDoesNotExist() {
        // Act
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        // Assert
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testSaveUser() {
        // Arrange
        User newUser = new User();
        newUser.setFirstName("Jane");
        newUser.setLastName("Smith");
        newUser.setEmail("jane.smith@example.com");
        newUser.setPassword("password123");
        newUser.setRole("ADMIN");
        newUser.setEnabled(false);

        // Act
        User savedUser = userRepository.save(newUser);

        // Assert
        assertNotNull(savedUser.getId());
        assertEquals("jane.smith@example.com", savedUser.getEmail());
    }

    @Test
    void testDeleteUser() {
        // Act
        userRepository.delete(testUser);

        // Assert
        Optional<User> deletedUser = userRepository.findByEmail("john.doe@example.com");
        assertFalse(deletedUser.isPresent());
    }
}

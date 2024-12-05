package com.wavebank.wave.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        User user = new User();

        // Assert
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getRole());
        assertFalse(user.isEnabled());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange & Act
        User user = new User(1L, "John", "Doe", "john.doe@example.com", "password", "USER", true);

        // Assert
        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("USER", user.getRole());
        assertTrue(user.isEnabled());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        User user = new User();

        // Act
        user.setId(1L);
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setEmail("jane.smith@example.com");
        user.setPassword("securePassword");
        user.setRole("ADMIN");
        user.setEnabled(true);

        // Assert
        assertEquals(1L, user.getId());
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("jane.smith@example.com", user.getEmail());
        assertEquals("securePassword", user.getPassword());
        assertEquals("ADMIN", user.getRole());
        assertTrue(user.isEnabled());
    }

    @Test
    void testIsEnabledDefault() {
        // Arrange & Act
        User user = new User();

        // Assert
        assertFalse(user.isEnabled(), "Default isEnabled value should be false");
    }
}

package com.restaurant.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.restaurant.model.Users;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserRepository userRepository;

    @Test
    void testFindByUsername_Found() {
        Users user = new Users();
        user.setUserId(1);
        user.setUsername("testuser");

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), eq("testuser")))
                .thenReturn(Collections.singletonList(user));

        Users result = userRepository.findByUsername("testuser");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals(Integer.valueOf(1), result.getUserId());
    }

    @Test
    void testFindByUsername_NotFound() {
        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), eq("nonexistent")))
                .thenReturn(Collections.emptyList());

        Users result = userRepository.findByUsername("nonexistent");

        assertNull(result);
    }

    @Test
    void testExistsByUsername_True() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq("existinguser")))
                .thenReturn(1);

        boolean exists = userRepository.existsByUsername("existinguser");

        assertTrue(exists);
    }

    @Test
    void testExistsByUsername_False() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq("unknown")))
                .thenReturn(0);

        boolean exists = userRepository.existsByUsername("unknown");

        assertFalse(exists);
    }

    @Test
    void testSaveUser() {
        Users user = new Users();
        user.setFullName("John Doe");
        user.setUsername("johndoe");
        user.setPassword("secret");
        user.setRole("STAFF");
        user.setMobile("9876543210");
        user.setEmail("john@example.com");
        user.setStatus("ACTIVE");

        when(jdbcTemplate.update(anyString(), eq("John Doe"), eq("johndoe"), eq("secret"), eq("STAFF"), eq("9876543210"), eq("john@example.com"), eq("ACTIVE")))
                .thenReturn(1);

        int rows = userRepository.save(user);

        assertEquals(1, rows);
    }

    @Test
    void testDeleteUser() {
        when(jdbcTemplate.update(anyString(), eq(10)))
                .thenReturn(1);

        int rows = userRepository.delete(10);

        assertEquals(1, rows);
    }
}

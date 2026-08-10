package com.restaurant.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Users;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // =====================================================
    // FIND BY USERNAME
    // =====================================================

    public Users findByUsername(String username) {

        String sql = """
                SELECT
                    user_id AS userId,
                    full_name AS fullName,
                    username,
                    password,
                    role,
                    mobile,
                    email,
                    status
                FROM users
                WHERE username = ?
                """;

        List<Users> list = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Users.class),
                username
        );

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    // =====================================================
    // CHECK USERNAME
    // =====================================================

    public boolean existsByUsername(String username) {

        String sql = """
                SELECT COUNT(*)
                FROM users
                WHERE username = ?
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                username
        );

        return count != null && count > 0;
    }

    // =====================================================
    // SAVE USER
    // =====================================================

    public int save(Users user) {

        String sql = """
                INSERT INTO users
                (
                    full_name,
                    username,
                    password,
                    role,
                    mobile,
                    email,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        return jdbcTemplate.update(
                sql,
                user.getFullName(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getMobile(),
                user.getEmail(),
                user.getStatus()
        );
    }

    // =====================================================
    // GET ALL USERS
    // =====================================================

    public List<Users> findAll() {

        String sql = """
                SELECT
                    user_id AS userId,
                    full_name AS fullName,
                    username,
                    password,
                    role,
                    mobile,
                    email,
                    status
                FROM users
                ORDER BY user_id DESC
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Users.class)
        );
    }

    // =====================================================
    // GET USER BY ID
    // =====================================================

    public Users findById(Integer id) {

        String sql = """
                SELECT
                    user_id AS userId,
                    full_name AS fullName,
                    username,
                    password,
                    role,
                    mobile,
                    email,
                    status
                FROM users
                WHERE user_id = ?
                """;

        List<Users> list = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Users.class),
                id
        );

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    // =====================================================
    // UPDATE USER
    // =====================================================

    public int update(Users user) {

        String sql = """
                UPDATE users
                SET
                    full_name = ?,
                    username = ?,
                    password = ?,
                    role = ?,
                    mobile = ?,
                    email = ?,
                    status = ?
                WHERE user_id = ?
                """;

        return jdbcTemplate.update(
                sql,
                user.getFullName(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getMobile(),
                user.getEmail(),
                user.getStatus(),
                user.getUserId()
        );
    }

    // =====================================================
    // UPDATE USER WITHOUT PASSWORD
    // =====================================================

    public int updateWithoutPassword(Users user) {

        String sql = """
                UPDATE users
                SET
                    full_name = ?,
                    username = ?,
                    role = ?,
                    mobile = ?,
                    email = ?,
                    status = ?
                WHERE user_id = ?
                """;

        return jdbcTemplate.update(
                sql,
                user.getFullName(),
                user.getUsername(),
                user.getRole(),
                user.getMobile(),
                user.getEmail(),
                user.getStatus(),
                user.getUserId()
        );
    }

    // =====================================================
    // UPDATE PASSWORD
    // =====================================================

    public int updatePassword(
            Integer id,
            String password) {

        String sql = """
                UPDATE users
                SET password = ?
                WHERE user_id = ?
                """;

        return jdbcTemplate.update(
                sql,
                password,
                id
        );
    }

    // =====================================================
    // DELETE USER
    // =====================================================

    public int delete(Integer id) {

        String sql = """
                DELETE FROM users
                WHERE user_id = ?
                """;

        return jdbcTemplate.update(
                sql,
                id
        );
    }
}
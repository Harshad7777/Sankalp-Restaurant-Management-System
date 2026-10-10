package com.restaurant.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Users;
import com.restaurant.repository.query.UserQueries;
import com.restaurant.security.SecurityUtil;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Users findByUsername(String username) {
        List<Users> list = jdbcTemplate.query(
                UserQueries.FIND_BY_USERNAME,
                new BeanPropertyRowMapper<>(Users.class),
                username);

        return list.isEmpty() ? null : list.get(0);
    }

    public Users findByUsernameOrEmail(String identity) {
        String sql = "SELECT user_id AS userId, restaurant_id AS restaurantId, full_name AS fullName, username, password, role, mobile, email, status FROM users WHERE LOWER(username) = LOWER(?) OR LOWER(email) = LOWER(?) LIMIT 1";
        List<Users> list = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Users.class),
                identity, identity);
        return list.isEmpty() ? null : list.get(0);
    }

    public boolean existsByUsername(String username) {
        Integer count = jdbcTemplate.queryForObject(
                UserQueries.EXISTS_BY_USERNAME,
                Integer.class,
                username);

        return count != null && count > 0;
    }

    public int save(Users user) {
        Integer resId = SecurityUtil.getOptionalCurrentRestaurantId();
        if (resId == null && user.getRestaurantId() != null && user.getRestaurantId() > 0) {
            resId = user.getRestaurantId();
        }
        if (resId == null) {
            resId = 1;
        }
        return jdbcTemplate.update(
                UserQueries.INSERT_USER,
                resId,
                user.getFullName(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getMobile(),
                user.getEmail(),
                user.getStatus());
    }

    public List<Users> findAll() {
        return jdbcTemplate.query(
                UserQueries.FIND_ALL_BY_RESTAURANT,
                new BeanPropertyRowMapper<>(Users.class),
                SecurityUtil.getCurrentRestaurantId());
    }

    public List<Users> findTeamUsers() {
        return jdbcTemplate.query(
                UserQueries.FIND_TEAM_USERS_BY_RESTAURANT,
                new BeanPropertyRowMapper<>(Users.class),
                SecurityUtil.getCurrentRestaurantId());
    }

    public Users findById(Integer id) {
        List<Users> list = jdbcTemplate.query(
                UserQueries.FIND_BY_ID,
                new BeanPropertyRowMapper<>(Users.class),
                id,
                SecurityUtil.getCurrentRestaurantId());

        return list.isEmpty() ? null : list.get(0);
    }

    public int update(Users user) {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        return jdbcTemplate.update(
                UserQueries.UPDATE_USER,
                user.getFullName(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getMobile(),
                user.getEmail(),
                user.getStatus(),
                user.getUserId(),
                resId);
    }

    public int updateWithoutPassword(Users user) {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        return jdbcTemplate.update(
                UserQueries.UPDATE_WITHOUT_PASSWORD,
                user.getFullName(),
                user.getUsername(),
                user.getRole(),
                user.getMobile(),
                user.getEmail(),
                user.getStatus(),
                user.getUserId(),
                resId);
    }

    public int updatePassword(Integer id, String password) {
        return jdbcTemplate.update(
                UserQueries.UPDATE_PASSWORD,
                password,
                id,
                SecurityUtil.getCurrentRestaurantId());
    }

    public int delete(Integer id) {
        return jdbcTemplate.update(
                UserQueries.DELETE_USER,
                id,
                SecurityUtil.getCurrentRestaurantId());
    }

    public List<Users> getUsersFilteredPage(com.restaurant.dto.PageRequestDTO req) {
        StringBuilder sql = new StringBuilder("""
                SELECT user_id AS userId, restaurant_id AS restaurantId, full_name AS fullName, username, password, role, mobile, email, status
                FROM users WHERE restaurant_id = ?
                """);
        java.util.List<Object> params = new java.util.ArrayList<>();
        params.add(SecurityUtil.getCurrentRestaurantId());

        if (req.getRole() != null && !req.getRole().isBlank()) {
            sql.append(" AND role = ?");
            params.add(req.getRole().trim().toUpperCase());
        }
        if (req.getStatus() != null && !req.getStatus().isBlank()) {
            sql.append(" AND status = ?");
            params.add(req.getStatus().trim().toUpperCase());
        }
        if (req.getSearch() != null && !req.getSearch().isBlank()) {
            sql.append(" AND (full_name LIKE ? OR username LIKE ? OR email LIKE ? OR mobile LIKE ?)");
            String term = "%" + req.getSearch().trim() + "%";
            params.add(term);
            params.add(term);
            params.add(term);
            params.add(term);
        }

        String sortColumn = "user_id";
        if ("username".equalsIgnoreCase(req.getSortBy())) sortColumn = "username";
        else if ("fullName".equalsIgnoreCase(req.getSortBy())) sortColumn = "full_name";
        else if ("role".equalsIgnoreCase(req.getSortBy())) sortColumn = "role";
        else if ("status".equalsIgnoreCase(req.getSortBy())) sortColumn = "status";

        sql.append(" ORDER BY ").append(sortColumn).append(" ").append(req.getSafeSortDirection());
        sql.append(" LIMIT ? OFFSET ?");
        params.add(req.getLimit());
        params.add(req.getOffset());

        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Users.class), params.toArray());
    }

    public long countUsersFiltered(com.restaurant.dto.PageRequestDTO req) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM users WHERE restaurant_id = ?");
        java.util.List<Object> params = new java.util.ArrayList<>();
        params.add(SecurityUtil.getCurrentRestaurantId());

        if (req.getRole() != null && !req.getRole().isBlank()) {
            sql.append(" AND role = ?");
            params.add(req.getRole().trim().toUpperCase());
        }
        if (req.getStatus() != null && !req.getStatus().isBlank()) {
            sql.append(" AND status = ?");
            params.add(req.getStatus().trim().toUpperCase());
        }
        if (req.getSearch() != null && !req.getSearch().isBlank()) {
            sql.append(" AND (full_name LIKE ? OR username LIKE ? OR email LIKE ? OR mobile LIKE ?)");
            String term = "%" + req.getSearch().trim() + "%";
            params.add(term);
            params.add(term);
            params.add(term);
            params.add(term);
        }

        Long count = jdbcTemplate.queryForObject(sql.toString(), Long.class, params.toArray());
        return count != null ? count : 0L;
    }
}
package com.restaurant.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Reservation;
import com.restaurant.repository.query.ReservationQueries;
import com.restaurant.security.SecurityUtil;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Reservation r = new Reservation();
        r.setReservationId(rs.getInt("reservation_id"));
        try {
            r.setRestaurantId(rs.getInt("restaurant_id"));
        } catch (SQLException ignored) {}
        r.setReservationCode(rs.getString("reservation_code"));
        r.setGuestName(rs.getString("guest_name"));
        r.setGuestPhone(rs.getString("guest_phone"));
        r.setGuestEmail(rs.getString("guest_email"));
        
        java.sql.Date bDate = rs.getDate("booking_date");
        if (bDate != null) {
            r.setBookingDate(bDate.toLocalDate());
        }

        r.setBookingTime(rs.getString("booking_time"));
        r.setGuestCount(rs.getInt("guest_count"));
        r.setSpecialRequest(rs.getString("special_request"));

        int tableId = rs.getInt("table_id");
        if (!rs.wasNull()) {
            r.setTableId(tableId);
        } else {
            r.setTableId(null);
        }
        
        r.setTableNumber(rs.getString("table_number"));
        r.setStatus(rs.getString("status"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            r.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            r.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return r;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                ReservationQueries.FIND_ALL,
                this::mapRow,
                SecurityUtil.getCurrentRestaurantId()
        );
    }

    public List<Reservation> findByStatus(String status) {
        return jdbcTemplate.query(
                ReservationQueries.FIND_BY_STATUS,
                this::mapRow,
                status,
                SecurityUtil.getCurrentRestaurantId()
        );
    }

    public Reservation findById(int id) {
        List<Reservation> list = jdbcTemplate.query(
                ReservationQueries.FIND_BY_ID,
                this::mapRow,
                id,
                SecurityUtil.getCurrentRestaurantId()
        );
        return list.isEmpty() ? null : list.get(0);
    }

    public Reservation findByCode(String code) {
        Integer resId = SecurityUtil.getOptionalCurrentRestaurantId();
        List<Reservation> list;
        if (resId != null) {
            list = jdbcTemplate.query(
                    ReservationQueries.FIND_BY_CODE,
                    this::mapRow,
                    code,
                    resId
            );
        } else {
            list = jdbcTemplate.query(
                    "SELECT r.*, t.table_number FROM table_reservation r LEFT JOIN restaurant_tables t ON r.table_id = t.table_id WHERE r.reservation_code = ?",
                    this::mapRow,
                    code
            );
        }
        return list.isEmpty() ? null : list.get(0);
    }

    public int save(Reservation r) {
        Integer resId = SecurityUtil.getOptionalCurrentRestaurantId();
        if (resId == null && r.getRestaurantId() != null && r.getRestaurantId() > 0) {
            resId = r.getRestaurantId();
        }
        if (resId == null) {
            resId = 1;
        }
        return jdbcTemplate.update(
                ReservationQueries.INSERT_RESERVATION,
                resId,
                r.getReservationCode(),
                r.getGuestName(),
                r.getGuestPhone(),
                r.getGuestEmail(),
                r.getBookingDate(),
                r.getBookingTime(),
                r.getGuestCount(),
                r.getSpecialRequest(),
                r.getTableId(),
                r.getStatus() != null ? r.getStatus() : "PENDING"
        );
    }

    public int updateStatus(int id, String status) {
        return jdbcTemplate.update(
                ReservationQueries.UPDATE_STATUS,
                status,
                id,
                SecurityUtil.getCurrentRestaurantId()
        );
    }

    public int assignTable(int id, Integer tableId) {
        return jdbcTemplate.update(
                ReservationQueries.ASSIGN_TABLE,
                tableId,
                id,
                SecurityUtil.getCurrentRestaurantId()
        );
    }

    public int delete(int id) {
        return jdbcTemplate.update(
                ReservationQueries.DELETE_RESERVATION,
                id,
                SecurityUtil.getCurrentRestaurantId()
        );
    }

    public boolean existsByCode(String code) {
        Integer resId = SecurityUtil.getOptionalCurrentRestaurantId();
        Integer count;
        if (resId != null) {
            count = jdbcTemplate.queryForObject(
                    ReservationQueries.EXISTS_BY_CODE,
                    Integer.class,
                    code,
                    resId
            );
        } else {
            count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM table_reservation WHERE reservation_code = ?",
                    Integer.class,
                    code
            );
        }
        return count != null && count > 0;
    }
}

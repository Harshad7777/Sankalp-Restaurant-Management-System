package com.restaurant.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Reservation;
import com.restaurant.repository.query.ReservationQueries;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Reservation r = new Reservation();
        r.setReservationId(rs.getInt("reservation_id"));
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
                this::mapRow
        );
    }

    public List<Reservation> findByStatus(String status) {
        return jdbcTemplate.query(
                ReservationQueries.FIND_BY_STATUS,
                this::mapRow,
                status
        );
    }

    public Reservation findById(int id) {
        List<Reservation> list = jdbcTemplate.query(
                ReservationQueries.FIND_BY_ID,
                this::mapRow,
                id
        );
        return list.isEmpty() ? null : list.get(0);
    }

    public Reservation findByCode(String code) {
        List<Reservation> list = jdbcTemplate.query(
                ReservationQueries.FIND_BY_CODE,
                this::mapRow,
                code
        );
        return list.isEmpty() ? null : list.get(0);
    }

    public int save(Reservation r) {
        return jdbcTemplate.update(
                ReservationQueries.INSERT_RESERVATION,
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
                id
        );
    }

    public int assignTable(int id, Integer tableId) {
        return jdbcTemplate.update(
                ReservationQueries.ASSIGN_TABLE,
                tableId,
                id
        );
    }

    public int delete(int id) {
        return jdbcTemplate.update(
                ReservationQueries.DELETE_RESERVATION,
                id
        );
    }

    public boolean existsByCode(String code) {
        Integer count = jdbcTemplate.queryForObject(
                ReservationQueries.EXISTS_BY_CODE,
                Integer.class,
                code
        );
        return count != null && count > 0;
    }
}

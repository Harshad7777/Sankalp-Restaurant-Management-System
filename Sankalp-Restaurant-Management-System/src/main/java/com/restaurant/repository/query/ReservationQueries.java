package com.restaurant.repository.query;

public class ReservationQueries {

    public static final String FIND_ALL =
            "SELECT r.*, t.table_number FROM table_reservation r " +
            "LEFT JOIN restaurant_tables t ON r.table_id = t.table_id " +
            "WHERE r.restaurant_id = ? " +
            "ORDER BY r.created_at DESC";

    public static final String FIND_BY_STATUS =
            "SELECT r.*, t.table_number FROM table_reservation r " +
            "LEFT JOIN restaurant_tables t ON r.table_id = t.table_id " +
            "WHERE r.status = ? AND r.restaurant_id = ? " +
            "ORDER BY r.created_at DESC";

    public static final String FIND_BY_ID =
            "SELECT r.*, t.table_number FROM table_reservation r " +
            "LEFT JOIN restaurant_tables t ON r.table_id = t.table_id " +
            "WHERE r.reservation_id = ? AND r.restaurant_id = ?";

    public static final String FIND_BY_CODE =
            "SELECT r.*, t.table_number FROM table_reservation r " +
            "LEFT JOIN restaurant_tables t ON r.table_id = t.table_id " +
            "WHERE r.reservation_code = ? AND r.restaurant_id = ?";

    public static final String INSERT_RESERVATION =
            "INSERT INTO table_reservation " +
            "(restaurant_id, reservation_code, guest_name, guest_phone, guest_email, booking_date, booking_time, guest_count, special_request, table_id, status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_STATUS =
            "UPDATE table_reservation SET status = ? WHERE reservation_id = ? AND restaurant_id = ?";

    public static final String ASSIGN_TABLE =
            "UPDATE table_reservation SET table_id = ? WHERE reservation_id = ? AND restaurant_id = ?";

    public static final String UPDATE_RESERVATION =
            "UPDATE table_reservation SET guest_name = ?, guest_phone = ?, booking_date = ?, " +
            "booking_time = ?, guest_count = ?, special_request = ?, table_id = ?, status = ? " +
            "WHERE reservation_id = ? AND restaurant_id = ?";

    public static final String DELETE_RESERVATION =
            "DELETE FROM table_reservation WHERE reservation_id = ? AND restaurant_id = ?";

    public static final String COUNT_BY_STATUS =
            "SELECT COUNT(*) FROM table_reservation WHERE status = ? AND restaurant_id = ?";

    public static final String EXISTS_BY_CODE =
            "SELECT COUNT(*) FROM table_reservation WHERE reservation_code = ? AND restaurant_id = ?";
}

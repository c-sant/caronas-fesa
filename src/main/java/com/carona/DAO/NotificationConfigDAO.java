package com.carona.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.carona.models.AvailableWeekdaysModel;
import com.carona.models.LocationModel;
import com.carona.models.NotificationConfigModel;
import com.carona.models.UserModel;

public class NotificationConfigDAO implements GenericDAO<NotificationConfigModel> {
    private static final String SELECT_SQL = "SELECT * FROM [NotificationConfig] WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM [NotificationConfig]";

    private static final String UPDATE_SQL = "UPDATE [NotificationConfig] SET " +
        "user_id = ? , " +
        "receive_notification = ? , " +
        "place_of_departure = ? , " +
        "max_distance_in_km = ? , " +
        "available_weekdays = ? , " +
        "initial_departure_time = ? , " +
        "final_departure_time = ? " +
        "WHERE id = ? ";


    private static final String DELETE_SQL = "DELETE FROM [NotificationConfig] WHERE id = ?";

    private static final String INSERT_SQL = "INSERT INTO [NotificationConfig] VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    LocationDAO locationDAO = new LocationDAO();
    AvailableWeekdaysDAO availableWeekdaysDAO = new AvailableWeekdaysDAO();

    @Override
    public void insert(NotificationConfigModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            
            locationDAO.insert(model.getPlaceOfDeparture());
            availableWeekdaysDAO.insert(model.getAvailableWeekdays());
            
            conn = Connector.getInstance();
            ps = conn.prepareStatement(INSERT_SQL);

            model.setId(getNextId(conn));

            ps.setInt(1, model.getId());
            ps.setString(2, model.getUserModel().getId());
            ps.setBoolean(3, model.getReceiveNotification());
            ps.setInt(4, model.getPlaceOfDeparture().getId());
            ps.setDouble(5, model.getMaxDistanceInKm());
            ps.setDouble(6, model.getAvailableWeekdays().getId());
            ps.setString(7, model.getInitialDepartureTime().toString());
            ps.setString(8, model.getFinalDepartureTime().toString());

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    private int getNextId(Connection conn) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT MAX(id) + 1 as next_id FROM [NotificationConfig]");
            ResultSet result = ps.executeQuery();
    
            return result.getInt("next_id");
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    @Override
    public void update(NotificationConfigModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = Connector.getInstance();

            locationDAO.update(model.getPlaceOfDeparture());
            availableWeekdaysDAO.update(model.getAvailableWeekdays());

            ps = conn.prepareStatement(UPDATE_SQL);
            
            ps.setString(1, model.getUserModel().getId());
            ps.setBoolean(2, model.getReceiveNotification());
            ps.setInt(3, model.getPlaceOfDeparture().getId());
            ps.setDouble(4, model.getMaxDistanceInKm());
            ps.setDouble(5, model.getAvailableWeekdays().getId());
            ps.setString(6, model.getInitialDepartureTime().toString());
            ps.setString(7, model.getFinalDepartureTime().toString());
            ps.setInt(8, model.getId());


            ps.executeUpdate();

        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public void remove(NotificationConfigModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();
            ps = conn.prepareStatement(DELETE_SQL);

            ps.setInt(1, model.getId());

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public NotificationConfigModel readById(NotificationConfigModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();
            ps = conn.prepareStatement(SELECT_SQL);

            ps.setInt(1, model.getId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return convertToModel(rs);
            }

            return null;
        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public List<NotificationConfigModel> readAll() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        List<NotificationConfigModel> posts = new ArrayList<NotificationConfigModel>();

        try {
            conn = Connector.getInstance();

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);

            while (rs.next()) {
                posts.add(convertToModel(rs));
            }

            return posts;
        } finally {
            if (stmt != null) {
                stmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    public Boolean notificationConfigExistsForUser(UserModel user) throws SQLException {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = Connector.getInstance();

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT COUNT(1) as notificationsConfig FROM [NotificationConfig] WHERE user_id = '" + user.getId() + "'");

            while (rs.next()) {
                return rs.getInt("notificationsConfig") >= 1;
            }

            return null;
        } finally {
            if (stmt != null) {
                stmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    protected NotificationConfigModel convertToModel(ResultSet rs) throws SQLException {
        LocationModel placeOfDeparture = locationDAO.readById(new LocationModel(rs.getInt("place_of_departure")));
        AvailableWeekdaysModel availableWeekdays = availableWeekdaysDAO.readById(new AvailableWeekdaysModel(rs.getInt("available_weekdays")));

        LocalTime initialDepartureTime = LocalTime.parse(rs.getString("initial_departure_time"));
        LocalTime finalDepartureTime = LocalTime.parse(rs.getString("final_departure_time"));

        return new NotificationConfigModel(
                rs.getInt("id"),
                new UserModel(rs.getString("user_id")),
                rs.getBoolean("receive_notification"),
                placeOfDeparture,
                rs.getDouble("max_distance_in_km"),
                availableWeekdays,
                initialDepartureTime,
                finalDepartureTime
                );
    }    
}

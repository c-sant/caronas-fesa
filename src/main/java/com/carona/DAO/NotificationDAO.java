package com.carona.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.carona.filters.AvailableWeekdaysFilter;
import com.carona.filters.LocationFilter;
import com.carona.models.NotificationModel;
import com.carona.models.PostModel;
import com.carona.models.UserModel;

public class NotificationDAO {
    private static final String INSERT_SQL = "INSERT INTO Notification(viewed, subscriber, post, notification_time) " +
    "SELECT ?, u.id, ?, ? " + 
    "FROM [User] u " +
    "INNER JOIN [NotificationConfig] n ON u.id = n.user_id " +
    "INNER JOIN [Location] l ON n.place_of_departure = l.id " +
    "INNER JOIN [AvailableWeekdays] awd ON n.available_weekdays = awd.id " +
    "WHERE " +
        "(n.receive_notification = 1) " +
        "AND " +
        "(l.latitude between ? and ? and l.longitude between ? and ?) " +
        "AND " +
        " ? " +
        "AND " +
        "( ? BETWEEN n.initial_departure_time and n.final_departure_time)";

    public void createMultipleNotifications(PostModel post) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();
            ps = conn.prepareStatement(INSERT_SQL);

            // Campos default para preenchimento de notificação
            ps.setBoolean(1, false);
            ps.setInt(2, post.getId());
            ps.setString(3, LocalDateTime.now().toString());

            // Filtro de distância do local de partida
            Double lat = post.getPlaceOfDeparture().getLatitude();
            ps.setString(4, lat.toString() + " - (max_distance_in_km / 111.0)");
            ps.setString(5, lat.toString() + " + (max_distance_in_km / 111.0)");
    
            Double lon = post.getPlaceOfDeparture().getLongitude();
            ps.setString(6, lon.toString() + " - (max_distance_in_km / 85.0)");
            ps.setString(7, lon.toString() + " + (max_distance_in_km / 85.0)");

            // Filtro para dias da semana disponíveis
            AvailableWeekdaysFilter availableWeekdaysFilter = new AvailableWeekdaysFilter(post.getAvailableWeekdays());
            ps.setString(8, availableWeekdaysFilter.generateSqlFilter());

            // Filtro para horário de viagem compatível
            ps.setString(9, post.getDepartureTime().toString());

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

    public List<NotificationModel> readByUser(UserModel user) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        List<NotificationModel> models = new ArrayList<NotificationModel>();

        try {
            conn = Connector.getInstance();

            ps = conn.prepareStatement("SELECT * FROM [Notification] WHERE subscriber = ?");

            ps.setString(1, user.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                models.add(convertToModel(rs));
            }

            return models;
        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    public Integer readUnreadNotificationsFromUser(UserModel user) throws SQLException {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = Connector.getInstance();

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT COUNT(1) as unread_notifications FROM [Notification] WHERE subscriber = '" + user.getId() + "' AND viewed = 0");

            while (rs.next()) {
                return rs.getInt("unread_notifications");
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

    
    public void setUserNotificationsViewed(UserModel user) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = Connector.getInstance();
            ps = conn.prepareStatement("UPDATE [Notification] SET viewed = 1 WHERE subscriber = ?");
            
            ps.setString(1, user.getId());

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

    protected NotificationModel convertToModel(ResultSet rs) throws SQLException {
        LocalDateTime notificationTime = LocalDateTime.parse(rs.getString("notification_time"));   
        
        return new NotificationModel(
                rs.getInt("id"),
                new UserModel(rs.getString("subscriber")),
                new PostModel(rs.getInt("post")),
                rs.getBoolean("viewed"),
                notificationTime
            );
    }
}

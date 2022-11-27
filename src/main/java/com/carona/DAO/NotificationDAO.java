package com.carona.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.carona.models.NotificationModel;
import com.carona.models.PostModel;
import com.carona.models.UserModel;

public class NotificationDAO implements GenericDAO<NotificationModel> {
    private static final String SELECT_SQL = "SELECT * FROM [Notification] WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM [Notification]";

    private static final String UPDATE_LOCATION_SQL = "UPDATE [Notification] SET " +
        "viewed = ? , " +
        "subscriber = ? , " +
        "post = ? , " +
        "notification_time = ? " +
        "WHERE id = ? ";

    private static final String DELETE_SQL = "DELETE FROM [Notification] WHERE id = ?";

    private static final String INSERT_SQL = "INSERT INTO [Notification] VALUES (?, ?, ?, ?, ?)";

    @Override
    public void insert(NotificationModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();

            ps = conn.prepareStatement(INSERT_SQL);

            model.setId(getNextId(conn));
            ps.setInt(1, model.getId());
            ps.setBoolean(2, model.getViewed());
            ps.setString(3, model.getSubscriber().getId());
            ps.setInt(4, model.getPost().getId());
            ps.setString(5, model.getNotificationTime().toString());

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
            ps = conn.prepareStatement("SELECT MAX(id) + 1 as next_id FROM [Notification]");
            ResultSet result = ps.executeQuery();
    
            return result.getInt("next_id");
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    @Override
    public void update(NotificationModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = Connector.getInstance();
            ps = conn.prepareStatement(UPDATE_LOCATION_SQL);
            
            ps.setBoolean(1, model.getViewed());
            ps.setString(2, model.getSubscriber().getId());
            ps.setInt(3, model.getPost().getId());
            ps.setString(4, model.getNotificationTime().toString());
            ps.setInt(5, model.getId());

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
    public void remove(NotificationModel model) throws SQLException {
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
    public NotificationModel readById(NotificationModel model) throws SQLException {
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
    public List<NotificationModel> readAll() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        List<NotificationModel> models = new ArrayList<NotificationModel>();

        try {
            conn = Connector.getInstance();

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);

            while (rs.next()) {
                models.add(convertToModel(rs));
            }

            return models;
        } finally {
            if (stmt != null) {
                stmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    public List<NotificationModel> readByUser(UserModel user) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        List<NotificationModel> models = new ArrayList<NotificationModel>();

        try {
            conn = Connector.getInstance();

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM [Notification] WHERE subscriber = " + user.getId());

            while (rs.next()) {
                models.add(convertToModel(rs));
            }

            return models;
        } finally {
            if (stmt != null) {
                stmt.close();
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

            ResultSet rs = stmt.executeQuery("SELECT COUNT(1) as unread_notifications FROM [Notification] WHERE subscriber = " + user.getId() + " AND viewed = 0");

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
        LocalTime notificationTime = LocalTime.parse(rs.getString("notification_time"));   
        
        return new NotificationModel(
                rs.getInt("id"),
                new UserModel(rs.getString("subscriber")),
                new PostModel(rs.getInt("post")),
                rs.getBoolean("viewed"),
                notificationTime
            );
    }
}

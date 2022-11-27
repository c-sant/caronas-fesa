package com.carona.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.carona.models.AvailableWeekdaysModel;

public class AvailableWeekdaysDAO implements GenericDAO<AvailableWeekdaysModel> {

    private static final String SELECT_SQL = "SELECT * FROM [AvailableWeekdays] WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM [AvailableWeekdays]";

    private static final String UPDATE_SQL = "UPDATE [AvailableWeekdays] SET " +
        "sunday = ? , " +
        "monday = ? , " +
        "tuesday = ? , " +
        "wednesday = ? , " +
        "thursday = ? , " +
        "friday = ? , " +
        "saturday = ? " +
        "WHERE id = ?";

    private static final String DELETE_SQL = "DELETE FROM [AvailableWeekdays] WHERE id = ?";

    private static final String INSERT_SQL = "INSERT INTO [AvailableWeekdays] VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public AvailableWeekdaysDAO() {

    }

    @Override
    public void insert(AvailableWeekdaysModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();

            ps = conn.prepareStatement(INSERT_SQL);

            model.setId(getNextId(conn));
            ps.setInt(1, model.getId());
            ps.setBoolean(2, model.getSunday());
            ps.setBoolean(3, model.getMonday());
            ps.setBoolean(4, model.getTuesday());
            ps.setBoolean(5, model.getWednesday());
            ps.setBoolean(6, model.getThursday());
            ps.setBoolean(7, model.getFriday());
            ps.setBoolean(8, model.getSaturday());

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
            ps = conn.prepareStatement("SELECT MAX(id) + 1 as next_id FROM [AvailableWeekdays]");
            ResultSet result = ps.executeQuery();
    
            return result.getInt("next_id");
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }
    
    @Override
    public void update(AvailableWeekdaysModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = Connector.getInstance();
            ps = conn.prepareStatement(UPDATE_SQL);
            
            ps.setBoolean(1, model.getSunday());
            ps.setBoolean(2, model.getMonday());
            ps.setBoolean(3, model.getTuesday());
            ps.setBoolean(4, model.getWednesday());
            ps.setBoolean(5, model.getThursday());
            ps.setBoolean(6, model.getFriday());
            ps.setBoolean(7, model.getSaturday());
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
    public void remove(AvailableWeekdaysModel model) throws SQLException {
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
    public AvailableWeekdaysModel readById(AvailableWeekdaysModel model) throws SQLException {
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
    public List<AvailableWeekdaysModel> readAll() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        List<AvailableWeekdaysModel> availableWeekdays = new ArrayList<AvailableWeekdaysModel>();

        try {
            conn = Connector.getInstance();

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);

            while (rs.next()) {
                availableWeekdays.add(convertToModel(rs));
            }

            return availableWeekdays;
        } finally {
            if (stmt != null) {
                stmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    protected AvailableWeekdaysModel convertToModel(ResultSet rs) throws SQLException {
        return new AvailableWeekdaysModel(
                rs.getInt("Id"),
                rs.getBoolean("sunday"),
                rs.getBoolean("monday"),
                rs.getBoolean("tuesday"),
                rs.getBoolean("wednesday"),
                rs.getBoolean("thursday"),
                rs.getBoolean("friday"),
                rs.getBoolean("saturday"));
    }
}

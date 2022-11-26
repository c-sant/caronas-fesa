package com.carona.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.carona.models.LocationModel;

public class LocationDAO implements GenericDAO<LocationModel> {

    private static final String SELECT_SQL = "SELECT * FROM [Location] WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM [Location]";

    private static final String UPDATE_USER_SQL = "UPDATE [Location] SET " +
        "latitude = ? , " +
        "longitude = ? " +
        "WHERE id = ? ";

    private static final String DELETE_SQL = "DELETE FROM [Location] WHERE id = ?";

    private static final String INSERT_SQL = "INSERT INTO [Location] VALUES (?, ?, ?)";

    public LocationDAO() {

    }

    @Override
    public void insert(LocationModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();

            ps = conn.prepareStatement(INSERT_SQL);

            model.setId(getNextId(conn));
            ps.setInt(1, model.getId());
            ps.setDouble(2, model.getLatitude());
            ps.setDouble(3, model.getLongitude());

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
            ps = conn.prepareStatement("SELECT MAX(id) + 1 as next_id FROM [Location]");
            ResultSet result = ps.executeQuery();
    
            return result.getInt("next_id");
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }
    
    @Override
    public void update(LocationModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = Connector.getInstance();
            ps = conn.prepareStatement(UPDATE_USER_SQL);
            
            ps.setDouble(1, model.getLatitude());
            ps.setDouble(2, model.getLongitude());
            ps.setInt(3, model.getId());

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
    public void remove(LocationModel model) throws SQLException {
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
    public LocationModel readById(LocationModel model) throws SQLException {
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
    public List<LocationModel> readAll() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        List<LocationModel> users = new ArrayList<LocationModel>();

        try {
            conn = Connector.getInstance();

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);

            while (rs.next()) {
                users.add(convertToModel(rs));
            }

            return users;
        } finally {
            if (stmt != null) {
                stmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    protected LocationModel convertToModel(ResultSet rs) throws SQLException {
        return new LocationModel(
                rs.getInt("id"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude"));
    }
}

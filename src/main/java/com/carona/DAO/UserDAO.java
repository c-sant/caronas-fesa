package com.carona.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.carona.models.Course;
import com.carona.models.UserModel;

public class UserDAO implements GenericDAO<UserModel> {

    private static final String SELECT_SQL = "SELECT * FROM [User] WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM [User]";

    private static final String UPDATE_PASSWORD_SQL = "UPDATE [User] SET password = ? WHERE id = ?";
    private static final String UPDATE_USER_SQL = "UPDATE [User] SET " +
        "name = ? , " +
        "description = ? , " +
        "course = ? , " +
        "phone_number = ? , " +
        "phone_number = ? , " +
        "WHERE id = ? ";

    private static final String DELETE_SQL = "DELETE FROM [User] WHERE id = ?";

    private static final String INSERT_SQL = "INSERT INTO [User] VALUES (?, ?, ?, ?, ?, ?)";

    @Override
    public void insert(UserModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();

            ps = conn.prepareStatement(INSERT_SQL);

            ps.setString(1, model.getId());
            ps.setString(2, model.getName());
            ps.setString(3, model.getDescription());
            ps.setInt(4, model.getCourse().getValue());
            ps.setString(5, model.getPhoneNumber());
            ps.setString(6, model.getPassword());

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
    public void update(UserModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = Connector.getInstance();
            ps = conn.prepareStatement(UPDATE_USER_SQL);
            
            ps.setString(1, model.getName());
            ps.setString(2, model.getDescription());
            ps.setInt(3, model.getCourse().getValue());
            ps.setString(4, model.getPhoneNumber());
            ps.setString(5, model.getId());

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
    public void remove(UserModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();
            ps = conn.prepareStatement(DELETE_SQL);

            ps.setString(1, model.getId());

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
    public UserModel readById(UserModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();
            ps = conn.prepareStatement(SELECT_SQL);

            ps.setString(1, model.getId());

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
    public List<UserModel> readAll() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        List<UserModel> users = new ArrayList<UserModel>();

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

    public void updatePassword(UserModel e) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = Connector.getInstance();
            ps = conn.prepareStatement(UPDATE_PASSWORD_SQL);

            ps.setString(1, e.getPassword());
            ps.setString(2, e.getId());

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

    public UserModel readById(String id) throws SQLException {
        UserModel model = new UserModel();
        model.setId(id);

        return readById(model);
    }

    protected UserModel convertToModel(ResultSet rs) throws SQLException {
        return new UserModel(
                rs.getString("Id"),
                rs.getString("name"),
                rs.getString("description"),
                Course.fromInteger(rs.getInt("course")),
                rs.getString("phone_number"),
                rs.getString("password")
            );
    }
}

package com.carona.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.carona.models.CourseModel;
import com.carona.models.UserModel;

public class UserDAO extends BaseDAO<UserModel> {

    private static final String UPDATE_PASSWORD_SQL = "UPDATE [User] SET password = ? WHERE id = ?";

    @Override
    protected String getTableName() {
        return "User";
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO [" + getTableName() + "] VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE [" + getTableName() + "] SET " +
                "name = ? , " +
                "description = ? , " +
                "course = ? , " +
                "phone_number = ? " +
                "WHERE id = ? ";
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(PreparedStatement ps, UserModel model) throws SQLException {
        ps.setString(1, model.getId());
        ps.setString(2, model.getName());
        ps.setString(3, model.getDescription());
        ps.setInt(4, model.getCourse().getValue());
        ps.setString(5, model.getPhoneNumber());
        ps.setString(6, model.getPassword());

        return ps;
    }

    @Override
    protected PreparedStatement prepareStatementForUpdate(PreparedStatement ps, UserModel model) throws SQLException {
        ps.setString(1, model.getName());
        ps.setString(2, model.getDescription());
        ps.setInt(3, model.getCourse().getValue());
        ps.setString(4, model.getPhoneNumber());
        ps.setString(5, model.getId());

        return ps;
    }

    @Override
    protected PreparedStatement prepareStatementForRemove(PreparedStatement ps, UserModel model) throws SQLException {
        ps.setString(1, model.getId());

        return ps;
    }

    @Override
    protected PreparedStatement prepareStatementForRead(PreparedStatement ps, UserModel model) throws SQLException {
        ps.setString(1, model.getId());

        return ps;
    }

    @Override
    protected UserModel convertToModel(ResultSet rs) throws SQLException {
        return new UserModel(
                rs.getString("Id"),
                rs.getString("name"),
                rs.getString("description"),
                CourseModel.fromInteger(rs.getInt("course")),
                rs.getString("phone_number"),
                rs.getString("password"));
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

}

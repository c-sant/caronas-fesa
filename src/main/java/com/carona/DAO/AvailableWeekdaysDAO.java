package com.carona.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.carona.models.AvailableWeekdaysModel;

public class AvailableWeekdaysDAO extends BaseDAO<AvailableWeekdaysModel> {

    @Override
    protected String getTableName() {
        return "AvailableWeekdays";
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO [" + getTableName() + "] VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE [" + getTableName() + "] SET " +
                "post_id = ? , " +
                "sunday = ? , " +
                "monday = ? , " +
                "tuesday = ? , " +
                "wednesday = ? , " +
                "thursday = ? , " +
                "friday = ? , " +
                "saturday = ? , " +
                "WHERE id = ?";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM [" + getTableName() + "] WHERE id = ?";
    }

    @Override
    protected String getSelectSql() {
        return "SELECT * FROM [" + getTableName() + "] WHERE id = ?";
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(PreparedStatement ps, AvailableWeekdaysModel model)
            throws SQLException {
        ps.setInt(1, model.getId());
        ps.setInt(2, model.getPostId());
        ps.setBoolean(3, model.getSunday());
        ps.setBoolean(4, model.getMonday());
        ps.setBoolean(5, model.getTuesday());
        ps.setBoolean(6, model.getWednesday());
        ps.setBoolean(7, model.getThursday());
        ps.setBoolean(8, model.getFriday());
        ps.setBoolean(9, model.getSaturday());

        return ps;
    }

    @Override
    protected PreparedStatement prepareStatementForUpdate(PreparedStatement ps, AvailableWeekdaysModel model)
            throws SQLException {
        ps.setInt(1, model.getPostId());
        ps.setBoolean(2, model.getSunday());
        ps.setBoolean(3, model.getMonday());
        ps.setBoolean(4, model.getTuesday());
        ps.setBoolean(5, model.getWednesday());
        ps.setBoolean(6, model.getThursday());
        ps.setBoolean(7, model.getFriday());
        ps.setBoolean(8, model.getSaturday());
        ps.setInt(9, model.getId());

        return ps;
    }

    @Override
    protected PreparedStatement prepareStatementForRemove(PreparedStatement ps, AvailableWeekdaysModel model)
            throws SQLException {
        ps.setInt(1, model.getId());

        return ps;
    }

    @Override
    protected PreparedStatement prepareStatementForRead(PreparedStatement ps, AvailableWeekdaysModel model)
            throws SQLException {
        ps.setInt(1, model.getId());

        return null;
    }

    @Override
    protected AvailableWeekdaysModel convertToModel(ResultSet rs) throws SQLException {
        return new AvailableWeekdaysModel(
                rs.getInt("Id"),
                rs.getInt("postId"),
                rs.getBoolean("sunday"),
                rs.getBoolean("monday"),
                rs.getBoolean("tuesday"),
                rs.getBoolean("wednesday"),
                rs.getBoolean("thursday"),
                rs.getBoolean("friday"),
                rs.getBoolean("saturday"));
    }

}

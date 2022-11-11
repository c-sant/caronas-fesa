package com.carona.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.carona.models.PostModel;

public class PostDAO extends BaseDAO<PostModel> {

    @Override
    protected String getTableName() {
        return "Post";
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO [" + getTableName() + "] VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSql() {
        return null;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(PreparedStatement ps, PostModel model) throws SQLException {
        ps.setInt(1, model.getId());
        ps.setString(2, model.getTitle());
        ps.setString(3, model.getDescription());
        ps.setInt(4, model.getPlaceOfDeparture().getId());
        ps.setInt(5, model.getDestination().getId());

        return null;
    }

    @Override
    protected PreparedStatement prepareStatementForUpdate(PreparedStatement ps, PostModel model) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected PreparedStatement prepareStatementForRemove(PreparedStatement ps, PostModel model) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected PreparedStatement prepareStatementForRead(PreparedStatement ps, PostModel model) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected PostModel convertToModel(ResultSet rs) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

}
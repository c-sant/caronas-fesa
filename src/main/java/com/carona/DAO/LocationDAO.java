package com.carona.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.carona.models.LocationModel;

public class LocationDAO extends BaseDAO<Integer, LocationModel> {

    @Override
    protected String getTableName() {
        return "Location";
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO [" + getTableName() + "] VALUES (?, ?, ?)";
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE [" + getTableName() + "] SET " +
                "latitude = ? , " +
                "longitude = ? " +
                "WHERE id = ? ";
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(PreparedStatement ps, LocationModel model)
            throws SQLException {
        ps.setInt(1, model.getId());
        ps.setDouble(2, model.getLatitude());
        ps.setDouble(3, model.getLongitude());

        return ps;
    }

    @Override
    protected PreparedStatement prepareStatementForUpdate(PreparedStatement ps, LocationModel model)
            throws SQLException {
        ps.setDouble(1, model.getLatitude());
        ps.setDouble(2, model.getLongitude());
        ps.setInt(3, model.getId());

        return ps;
    }

    @Override
    protected PreparedStatement prepareStatementForRemove(PreparedStatement ps, LocationModel model)
            throws SQLException {
        ps.setInt(1, model.getId());

        return ps;
    }

    @Override
    protected PreparedStatement prepareStatementForRead(PreparedStatement ps, LocationModel model) throws SQLException {
        ps.setInt(1, model.getId());

        return ps;
    }

    @Override
    protected LocationModel convertToModel(ResultSet rs) throws SQLException {
        return new LocationModel(
                rs.getInt("id"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude"));
    }

    public LocationModel readById(Integer id) throws SQLException {
        LocationModel model = new LocationModel();
        model.setId(id);

        return readById(model);
    }

}

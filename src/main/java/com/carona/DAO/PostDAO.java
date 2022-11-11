package com.carona.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import com.carona.models.AvailableWeekdaysModel;
import com.carona.models.LocationModel;
import com.carona.models.PostModel;

public class PostDAO extends BaseDAO<Integer, PostModel> {

    @Override
    protected String getTableName() {
        return "Post";
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO [" + getTableName() + "] VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE [" + getTableName() + "] SET " +
                "title = ? , " +
                "description = ? , " +
                "place_of_departure = ? , " +
                "destination = ? , " +
                "available_weekdays = ? , " +
                "available_seats = ? , " +
                "departure_time = ? " +
                "WHERE id = ?";
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(PreparedStatement ps, PostModel model) throws SQLException {
        ps.setInt(1, model.getId());
        ps.setString(2, model.getTitle());
        ps.setString(3, model.getDescription());
        ps.setInt(4, model.getPlaceOfDeparture().getId());
        ps.setInt(5, model.getDestination().getId());
        ps.setInt(6, model.getAvailableWeekdays().getId());
        ps.setInt(7, model.getAvailableSeats());
        ps.setString(8, model.getDepartureTime().toString());

        return ps;
    }

    @Override
    protected PreparedStatement prepareStatementForUpdate(PreparedStatement ps, PostModel model) throws SQLException {
        ps.setString(1, model.getTitle());
        ps.setString(2, model.getDescription());
        ps.setInt(3, model.getPlaceOfDeparture().getId());
        ps.setInt(4, model.getDestination().getId());
        ps.setInt(5, model.getAvailableWeekdays().getId());
        ps.setInt(6, model.getAvailableSeats());
        ps.setString(7, model.getDepartureTime().toString());
        ps.setInt(8, model.getId());

        return ps;
    }

    @Override
    protected PreparedStatement prepareStatementForRemove(PreparedStatement ps, PostModel model) throws SQLException {
        ps.setInt(1, model.getId());

        return ps;
    }

    @Override
    protected PreparedStatement prepareStatementForRead(PreparedStatement ps, PostModel model) throws SQLException {
        ps.setInt(1, model.getId());

        return ps;
    }

    @Override
    protected PostModel convertToModel(ResultSet rs) throws SQLException {
        LocationDAO locationDAO = new LocationDAO();
        AvailableWeekdaysDAO availableWeekdaysDAO = new AvailableWeekdaysDAO();

        LocationModel placeOfDeparture = locationDAO.readById(rs.getInt("place_of_departure"));
        LocationModel destination = locationDAO.readById(rs.getInt("destination"));
        AvailableWeekdaysModel availableWeekdays = availableWeekdaysDAO.readById(rs.getInt("available_weekdays"));
        LocalTime departureTime = LocalTime.parse(rs.getString("departure_time"));

        return new PostModel(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                placeOfDeparture,
                destination,
                availableWeekdays,
                rs.getInt("available_seats"),
                departureTime);
    }

    public PostModel readById(Integer id) throws SQLException {
        PostModel model = new PostModel();
        model.setId(id);

        return readById(model);
    }
}

package com.carona.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.carona.models.PostModel;
import com.carona.models.UserModel;
import com.carona.models.LocationModel;
import com.carona.models.AvailableWeekdaysModel;

public class PostDAO implements GenericDAO<PostModel> {

    private static final String SELECT_SQL = "SELECT * FROM [Post] WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM [Post]";

    private static final String UPDATE_USER_SQL = "UPDATE [Post] SET " +
        "creator_id = ? , " +
        "title = ? , " +
        "description = ? , " +
        "place_of_departure = ? , " +
        "destination = ? , " +
        "available_weekdays = ? , " +
        "available_seats = ? , " +
        "departure_time = ? " +
        "WHERE id = ?";

    private static final String DELETE_SQL = "DELETE FROM [Post] WHERE id = ?";

    private static final String INSERT_SQL = "INSERT INTO [Post] VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private LocationDAO locationDAO = new LocationDAO();
    private AvailableWeekdaysDAO availableWeekdaysDAO = new AvailableWeekdaysDAO();

    public PostDAO() {

    }

    @Override
    public void insert(PostModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();
            
            locationDAO.insert(model.getPlaceOfDeparture());
            locationDAO.insert(model.getDestination());
            availableWeekdaysDAO.insert(model.getAvailableWeekdays());

            ps = conn.prepareStatement(INSERT_SQL);

            model.setId(getNextId(conn));
            ps.setInt(1, model.getId());
            ps.setString(2, model.getCreator().getId());
            ps.setString(3, model.getTitle());
            ps.setString(4, model.getDescription());
            ps.setInt(5, model.getPlaceOfDeparture().getId());
            ps.setInt(6, model.getDestination().getId());
            ps.setInt(7, model.getAvailableWeekdays().getId());
            ps.setInt(8, model.getAvailableSeats());
            ps.setString(9, model.getDepartureTime().toString());
    

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
            ps = conn.prepareStatement("SELECT MAX(id) + 1 as next_id FROM [Post]");
            ResultSet result = ps.executeQuery();
    
            return result.getInt("next_id");
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }
    
    @Override
    public void update(PostModel model) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = Connector.getInstance();

            locationDAO.update(model.getPlaceOfDeparture());
            locationDAO.update(model.getDestination());
            availableWeekdaysDAO.update(model.getAvailableWeekdays());

            ps = conn.prepareStatement(UPDATE_USER_SQL);
            
            ps.setString(1, model.getTitle());
            ps.setString(2, model.getDescription());
            ps.setInt(3, model.getPlaceOfDeparture().getId());
            ps.setInt(4, model.getDestination().getId());
            ps.setInt(5, model.getAvailableWeekdays().getId());
            ps.setInt(6, model.getAvailableSeats());
            ps.setString(7, model.getDepartureTime().toString());
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
    public void remove(PostModel model) throws SQLException {
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
    public PostModel readById(PostModel model) throws SQLException {
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
    public List<PostModel> readAll() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        List<PostModel> users = new ArrayList<PostModel>();

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

    protected PostModel convertToModel(ResultSet rs) throws SQLException {
        LocationModel placeOfDeparture = locationDAO.readById(new LocationModel(rs.getInt("place_of_departure")));
        LocationModel destination = locationDAO.readById(new LocationModel(rs.getInt("destination")));
        AvailableWeekdaysModel availableWeekdays = availableWeekdaysDAO.readById(new AvailableWeekdaysModel(rs.getInt("available_weekdays")));
        UserModel creator = new UserModel(rs.getString("creator_id"));

        LocalTime departureTime = LocalTime.parse(rs.getString("departure_time"));

        return new PostModel(
                rs.getInt("id"),
                creator,
                rs.getString("title"),
                rs.getString("description"),
                placeOfDeparture,
                destination,
                availableWeekdays,
                rs.getInt("available_seats"),
                departureTime);
    }
}


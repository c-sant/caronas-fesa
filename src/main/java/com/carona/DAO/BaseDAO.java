package com.carona.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.carona.models.AbstractModel;

public abstract class BaseDAO<T extends AbstractModel> implements GenericDAO<T> {
    protected abstract String getTableName();
    
    protected abstract String getInsertSql();
    protected abstract String getUpdateSql();
    protected abstract String getDeleteSql();
    protected abstract String getSelectSql();

    protected abstract PreparedStatement prepareStatementForInsert(PreparedStatement ps, T model) throws SQLException;
    protected abstract PreparedStatement prepareStatementForUpdate(PreparedStatement ps, T model) throws SQLException;
    protected abstract PreparedStatement prepareStatementForRemove(PreparedStatement ps, T model) throws SQLException;
    protected abstract PreparedStatement prepareStatementForRead(PreparedStatement ps, T model) throws SQLException;

    protected abstract T convertToModel(ResultSet rs) throws SQLException;

    private String selectAllSql() {
        return "SELECT * FROM [" + getTableName() + "]";
    }

    @Override
    public void insert(T e) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();
            
            ps = conn.prepareStatement(getInsertSql());

            ps = prepareStatementForInsert(ps, e);

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
    public void update(T e) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = Connector.getInstance();
            ps = conn.prepareStatement(getUpdateSql());

            ps = prepareStatementForUpdate(ps, e);

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
    public void remove(T e) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();
            ps = conn.prepareStatement(getDeleteSql());

            ps = prepareStatementForRemove(ps, e);
            
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
    public T readById(T e) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();
            ps = conn.prepareStatement(getSelectSql());

            ps = prepareStatementForRead(ps, e);

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
    public List<T> readAll() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        List<T> users = new ArrayList<T>();

        try {
            conn = Connector.getInstance();

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(selectAllSql());

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

    
}

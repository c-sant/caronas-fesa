package com.carona.services;

import java.sql.SQLException;
import java.util.List;

import com.carona.DAO.NotificationDAO;
import com.carona.models.NotificationModel;
import com.carona.models.UserModel;

public class NotificationService {
    NotificationDAO notificationDAO = new NotificationDAO();

    public List<NotificationModel> readUserNotifications(UserModel user) throws SQLException {
        return notificationDAO.readByUser(user);
    }

    public Integer countUserUnreadNotifications(UserModel user) throws SQLException {
        return notificationDAO.readUnreadNotificationsFromUser(user);
    }

    public void setUserNotificationsViewed(UserModel user) throws SQLException {
        notificationDAO.setUserNotificationsViewed(user);
    }
}

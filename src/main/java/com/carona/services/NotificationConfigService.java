package com.carona.services;

import java.sql.SQLException;
import java.util.List;

import com.carona.App;
import com.carona.DAO.NotificationConfigDAO;
import com.carona.DAO.UserDAO;
import com.carona.exceptions.BlankFieldsException;
import com.carona.exceptions.EntityAlreadyExistsException;
import com.carona.exceptions.EntityDoesNotExistException;
import com.carona.models.NotificationConfigModel;

public class NotificationConfigService {
    NotificationConfigDAO notificationDAO = new NotificationConfigDAO();
    UserDAO userDAO = new UserDAO();

    public void create(NotificationConfigModel notification) throws BlankFieldsException, EntityAlreadyExistsException, EntityDoesNotExistException, SQLException {
        Boolean notificationExists = notificationDAO.readById(notification) != null;

        if (notificationExists) {
            update(notification);
            // throw new EntityAlreadyExistsException("Notification já existe com id " + notification.getId()); 
        }
        validateNotification(notification);

        Boolean userDoesNotExists = userDAO.readById(notification.getUserModel()) == null;
        if (userDoesNotExists) {
            throw new EntityDoesNotExistException("Usuário com id = " + notification.getUserModel().getId() + " não existe!"); 
        }

        Boolean userAlreadyHasNotificationConfig = notificationDAO.notificationConfigExistsForUser(notification.getUserModel());
        if (userAlreadyHasNotificationConfig) {
            update(notification);
            // throw new EntityAlreadyExistsException("Usuário com id = " + notification.getUserModel().getId() + " já possui NotificationConfig"); 
        }

        notificationDAO.insert(notification);
    }

    public void update(NotificationConfigModel notification) throws SQLException, EntityDoesNotExistException, BlankFieldsException  {
        Boolean notificationDoesNotExist = notificationDAO.readById(notification) == null;

        if (notificationDoesNotExist) {
            throw new EntityDoesNotExistException("Notification não existe com id " + notification.getId()); 
        }
        validateNotification(notification);

        Boolean userDoesNotExists = userDAO.readById(notification.getUserModel()) == null;
        if (userDoesNotExists) {
            throw new EntityDoesNotExistException("Usuário com id = " + notification.getUserModel().getId() + " não existe!"); 
        }

        notificationDAO.update(notification);
    }

    public void remove(NotificationConfigModel notification) throws SQLException {
        notificationDAO.remove(notification);
    }

    public NotificationConfigModel readById(Integer id) throws SQLException {
        NotificationConfigModel notificationFound = notificationDAO.readById(new NotificationConfigModel(id));

        return notificationFound;
    }

    public List<NotificationConfigModel> readAll() throws SQLException {
        List<NotificationConfigModel> notificationsFound = notificationDAO.readAll();

        return notificationsFound;
    }

    public NotificationConfigModel readByUserId() throws SQLException{
        NotificationConfigModel model = notificationDAO.getNotificationConfigByUser(App.getUser());
        return model;
    }
    private void validateNotification(NotificationConfigModel notification) throws BlankFieldsException {
        if (notification.getUserModel() == null) {
            throw new BlankFieldsException("Usuário de configuração não especificado.");
        }

        if (notification.getReceiveNotification() == null) {
            throw new BlankFieldsException("Indicação de desejo de notificação não especificado.");
        }

        if (notification.getAvailableWeekdays() == null) {
            throw new BlankFieldsException("Dias da semana disponíveis não especificados.");
        }

        if (notification.getInitialDepartureTime() == null) {
            throw new BlankFieldsException("Tempo inicial de partida não especificado.");
        }

        if (notification.getFinalDepartureTime() == null) {
            throw new BlankFieldsException("Tempo limite de partida não especificado.");
        }

        if (notification.getMaxDistanceInKm() == null) {
            throw new BlankFieldsException("Distância máxima da partida não especificada.");
        }
        
        if (notification.getPlaceOfDeparture() == null) {
            throw new BlankFieldsException("Local de partida não especificado.");
        }
    }
}

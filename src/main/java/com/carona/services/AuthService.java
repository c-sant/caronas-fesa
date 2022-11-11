package com.carona.services;

import java.io.IOException;
import java.sql.SQLException;

import com.carona.DAO.UserDAO;
import com.carona.helpers.AuthHelper;
import com.carona.models.UserModel;

public class AuthService {
    public UserModel login(String ra, String password) throws Exception, IOException, SQLException {
        UserDAO userDAO = new UserDAO();

        UserModel userWithRA = new UserModel();
        userWithRA.setId(ra);

        UserModel user = userDAO.readById(userWithRA);
        if (user == null) {
            return null;
        }

        String currentPassword = user.getPassword();
        String passwordToCompare = AuthHelper.encryptPassword(password);

        if (currentPassword.equals(passwordToCompare)) {
            return user;
        }
        return null;
    }
}

package com.carona.services;

import java.sql.SQLException;

import com.carona.DAO.UserDAO;
import com.carona.exceptions.EntityAlreadyExistsException;
import com.carona.exceptions.EntityDoesNotExistException;
import com.carona.exceptions.InvalidPasswordComplexityException;
import com.carona.helpers.AuthHelper;
import com.carona.models.User;

public class UserService {
    public void register(User user) throws Exception, EntityAlreadyExistsException, InvalidPasswordComplexityException {
        UserDAO userDAO = new UserDAO();

        Boolean userExists = userDAO.readById(user) != null;

        if (userExists) {
            throw new EntityAlreadyExistsException("User already exists with id " + user.getId()); // Criar classe que
                                                                                                   // herda de
                                                                                                   // Exception.
        }

        String password = user.getPassword();
        if (password.length() < 8) {
            throw new InvalidPasswordComplexityException(
                    "Invalid password complexity from user with id " + user.getId());
        }

        user.setPassword(AuthHelper.encryptPassword(password));
        userDAO.insert(user);
    }

    public User getUserById(String id) throws Exception, SQLException {
        UserDAO userDAO = new UserDAO();
        User searchUser = new User();
        searchUser.setId(id);

        User userFound = userDAO.readById(searchUser);

        assertUserExistence(userFound);

        userFound.setPassword("");
        return userFound;
    }

    public void editUser(User user) throws Exception {
        UserDAO userDAO = new UserDAO();

        assertUserExistence(user);

        userDAO.update(user);
    }

    public void editPassword(User user) throws Exception {
        UserDAO userDAO = new UserDAO();

        assertUserExistence(user);

        String newPassword = AuthHelper.encryptPassword(user.getPassword());
        user.setPassword(newPassword);

        userDAO.updatePassword(user);
    }

    public void remove(User user) throws Exception {
        UserDAO userDAO = new UserDAO();
        userDAO.remove(user);
    }

    public void assertUserExistence(User user) throws SQLException, EntityDoesNotExistException {
        UserDAO userDAO = new UserDAO();

        Boolean userDoesNotExist = userDAO.readById(user) == null;

        if (userDoesNotExist) {
            // Criar classe que herda de Exception.
            throw new EntityDoesNotExistException("User does not exist to edit, with id " + user.getId());
        }
    }

}

package com.carona.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import com.carona.App;
import com.carona.models.User;
import com.carona.services.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EditUserController {

    @FXML
    TextField txtName;

    @FXML
    TextField txtPhoneNumber;

    @FXML
    TextArea txtDescription;

    @FXML
    Button btnUpdate;

    @FXML
    Button btnDelete;

    User user = App.getUser();

    @FXML
    public void initialize() {
        User user = App.getUser();
        txtName.setText(user.getName());
        txtPhoneNumber.setText(user.getPhoneNumber());
        txtDescription.setText(user.getDescription());
    }

    @FXML
    private void onUpdateUser() throws IOException, SQLException {
        UserService userService = new UserService();

        user.setName(txtName.getText());
        user.setDescription(txtDescription.getText());
        user.setPhoneNumber(txtPhoneNumber.getText());

        try {
            userService.editUser(user);
        } catch (Exception ex) {
            System.out.println("Nathan, coloca um handle de exception ai, pls");
        }
    }

    @FXML
    private void onDeleteUser() throws IOException, SQLException {
        UserService userService = new UserService();
        try {
            if (createAlert("Confirmar exclusão", "Tem certeza que deseja excluir a conta?")) {
                userService.remove(user);
            }
        } catch (Exception ex) {
            createAlertInformation();
        }
    }

    @FXML
    private void onLogout() throws IOException, SQLException {
        App.setUser(null);
        App.setRoot("loginScreen");
    }

    private Boolean createAlert(String title, String message) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText(title);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            App.setRoot("loginScreen");
            return true;
        } else {
            return false;
        }
    }

    private void createAlertInformation() throws IOException {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText("Erro");
        alert.setContentText("Algum erro ocorreu no momento da exclusão!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return;
        }
    }
}

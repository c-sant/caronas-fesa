package com.carona.controllers;

import com.carona.App;
import com.carona.models.UserModel;
import com.carona.services.AuthService;
import com.carona.services.NotificationConfigService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController {

    @FXML
    TextField txtName;
    @FXML
    PasswordField txtPassword;
    @FXML
    Button btnLogin;
    @FXML
    Button btnRegister;

    public void initialize() {
        txtName.setText("081200007");
        txtPassword.setText("12345678");
    }

    AuthService authService = new AuthService();
    NotificationConfigService notificationService = new NotificationConfigService();

    @FXML
    private void onLogin() throws Exception, IOException, SQLException {
        String ra = txtName.getText();
        String password = txtPassword.getText();

        UserModel user = authService.login(ra, password);

        if (user != null) {
            App.setUser(user);
            App.setNotificationConfig(notificationService.readByUserId());
            App.setRoot("mainScreen");
        } else {
            createAlert();
        }
    }

    @FXML
    private void onRegister() throws IOException {
        App.setRoot("registerScreen");
    }

    @FXML
    private void onKeyPressed(KeyEvent event) throws Exception {
        if (event.getCode().equals(KeyCode.ENTER)) {
            onLogin();
        }
    }

    private void createAlert() throws IOException {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Mensagem");
        alert.setHeaderText("Erro de login");
        alert.setContentText("Usuário inexistente");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            App.setRoot("loginScreen");
        }
    }
}

package com.carona.controllers;

import com.carona.App;
import com.carona.exceptions.BlankFieldsException;
import com.carona.exceptions.EntityAlreadyExistsException;
import com.carona.exceptions.InvalidFormatPhoneNumberException;
import com.carona.exceptions.InvalidFormatStudentIdException;
import com.carona.exceptions.InvalidPasswordComplexityException;
import com.carona.exceptions.PasswordNotMatchException;
import com.carona.models.CourseModel;
import com.carona.models.UserModel;
import com.carona.services.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RegisterController {

    @FXML
    TextField txtName;
    @FXML
    ComboBox cboCourse;
    @FXML
    TextField txtPhoneNumber;
    @FXML
    TextField txtStudentId;
    @FXML
    TextArea txtDescription;
    @FXML
    PasswordField txtPassword;
    @FXML
    PasswordField txtConfirmedPassword;
    @FXML
    Button btnRegister;
    @FXML
    Button btnCancel;

    Map<String, Integer> courseStrInt = new HashMap<String, Integer>();
    Map<Integer, String> courseIntStr = new HashMap<Integer, String>();

    @FXML
    public void initialize() {
        cboCourse.getItems().removeAll(cboCourse.getItems());
        cboCourse.getItems().addAll("Engenharia da Computação", "Engenharia de Alimentos", "Administração",
                "Engenharia de Controle e Automação");
        for (Integer i = 0; i < cboCourse.getItems().size(); i++) {
            courseStrInt.put((String) cboCourse.getItems().get(i), i);
            courseIntStr.put(i, (String) cboCourse.getItems().get(i));
        }

    }

    @FXML
    private void onRegister() throws Exception, EntityAlreadyExistsException, InvalidPasswordComplexityException {
        Boolean hasError = false;
        String message = "";
        try {
            validateRegexInTextFields();
        } catch (InvalidPasswordComplexityException err) {
            hasError = true;
            message = "Complexidade muito baixa de senha";
        } catch (InvalidFormatPhoneNumberException err) {
            hasError = true;
            message = "Formato inválido de telefone";
        } catch (InvalidFormatStudentIdException err) {
            hasError = true;
            message = "Formato inválido de RA";
        } catch (BlankFieldsException err) {
            hasError = true;
            message = "Ainda existe campos obrigatórios não preenchidos";
        } catch (PasswordNotMatchException err) {
            hasError = true;
            message = "Senhas não correspondem";
        }

        if (hasError) {
            createAlert(hasError, message);
            return;
        }

        UserService userService = new UserService();

        UserModel user = new UserModel(
                txtStudentId.getText(),
                txtName.getText(),
                txtDescription.getText(),
                CourseModel.fromInteger(courseStrInt.get(cboCourse.getValue())),
                txtPhoneNumber.getText(),
                txtPassword.getText());

        try {
            userService.register(user);
        } catch (EntityAlreadyExistsException err) {
            hasError = true;
            message = "Usuário existente com este Id";
        } catch (InvalidPasswordComplexityException err) {
            hasError = true;
            message = "Complexidade de Senha muita baixa";
        } catch (SQLException err) {
            hasError = true;
            message = "Erro ao salvar dados";
        }

        createAlert(hasError, message);
    }

    @FXML
    private void onCancel() throws IOException {
        App.setRoot("loginScreen");
    }

    private void createAlert(Boolean hasError, String message) throws IOException {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText("Status de Criação");
        alert.setContentText(!hasError ? "Criado com Sucesso" : message);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK && hasError.equals(false)) {
            App.setRoot("loginScreen");
        }
    }

    private void validateRegexInTextFields() throws Exception {
        final Pattern patternPassword = Pattern.compile("^[0-9a-zA-Z$*&@#]{8,}$");
        final Pattern patternPhoneNumber = Pattern.compile("^[(]{1}[0-9]{2}[)]{1} [0-9]{5}-[0-9]{4}$");
        final Pattern patternStudentId = Pattern.compile("^[0-9]{9}$");
        Boolean isValidPassword = patternPassword.matcher(txtPassword.getText()).find();
        Boolean isValidPhoneNumber = patternPhoneNumber.matcher(txtPhoneNumber.getText()).find();
        Boolean isValidStudentId = patternStudentId.matcher(txtStudentId.getText()).find();

        if (!isValidPassword) {
            throw new InvalidPasswordComplexityException("low complexity password");
        }

        if (!isValidPhoneNumber) {
            throw new InvalidFormatPhoneNumberException("invalid format number");
        }

        if (!isValidStudentId) {
            throw new InvalidFormatStudentIdException("invalid format studentId");
        }

        if (!validateBlankInTextFields()) {
            throw new BlankFieldsException("one or more fields is blank");
        }

        if (!validatePasswordIsEquals()) {
            throw new PasswordNotMatchException("password not match");
        }
    }

    private Boolean validateBlankInTextFields() {
        if (txtConfirmedPassword.getText().isEmpty() ||
                txtDescription.getText().isEmpty() ||
                txtName.getText().isEmpty() ||
                cboCourse.getSelectionModel().isEmpty()) {
            return false;
        }
        return true;
    }

    private Boolean validatePasswordIsEquals() {
        if (!txtPassword.getText().equals(txtConfirmedPassword.getText())) {
            return false;
        }
        return true;
    }
}

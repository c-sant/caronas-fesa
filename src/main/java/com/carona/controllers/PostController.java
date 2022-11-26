package com.carona.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.carona.App;
import com.carona.exceptions.BlankFieldsException;
import com.carona.exceptions.InvalidSelectionDaysException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class PostController {

    @FXML
    TextField txtNumbersOfPeople;

    @FXML
    TextField txtDepartureHour;

    @FXML
    TextField txtDepartureLocation;

    @FXML
    TextField txtArriveLocation;

    @FXML
    TextField chkSaturday;

    @FXML
    TextField chkTuesday;

    @FXML
    TextField chkThursday;

    @FXML
    TextField chkFriday;

    @FXML
    TextField chkMonday;

    @FXML
    TextField txtTitle;

    @FXML
    TextField chkWednesday;

    @FXML
    TextArea txtDescription;

    @FXML
    Button btnCancel;

    @FXML
    Button btnCreatePost;

    Map<String, Boolean> checkDaysMap = new HashMap<String, Boolean>();

    @FXML
    public void initialize() throws IOException {
        checkDaysMap.put("chkMonday"   , false);
        checkDaysMap.put("chkTuesday"  , false);
        checkDaysMap.put("chkWednesday", false);
        checkDaysMap.put("chkThursday" , false);
        checkDaysMap.put("chkFriday"   , false);
        checkDaysMap.put("chkSaturday" , false);
        chkMonday.setOnMouseClicked(event -> selectedOrUnselected((TextField) event.getSource()));
        chkTuesday.setOnMouseClicked(event -> selectedOrUnselected((TextField) event.getSource()));
        chkWednesday.setOnMouseClicked(event -> selectedOrUnselected((TextField) event.getSource()));
        chkThursday.setOnMouseClicked(event -> selectedOrUnselected((TextField) event.getSource()));
        chkFriday.setOnMouseClicked(event -> selectedOrUnselected((TextField) event.getSource()));
        chkSaturday.setOnMouseClicked(event -> selectedOrUnselected((TextField) event.getSource()));
    }

    private void selectedOrUnselected(TextField txtEvent){
        String chkId = (String)txtEvent.getId();
        Boolean isSelected = !checkDaysMap.get(chkId);
        TextField txtField = (TextField) App.getNode("#" + chkId);
        if(isSelected){
            txtField.getStyleClass().add("selected");
        }else{
            txtField.getStyleClass().remove("selected");
        }
        checkDaysMap.put(chkId, isSelected);
   }
    
    @FXML
    private void onCreatePost() throws IOException, BlankFieldsException, InvalidSelectionDaysException {
        Boolean hasError = false;
        String message = "";
        try{
            verifyFields();
        }catch(BlankFieldsException err){
            hasError = true;
            message = "Ainda existe campos obrigatórios não preenchidos";
        }catch(InvalidSelectionDaysException err){
            hasError = true;
            message = "Pelo menos um dia deve ser selecionado";
        }

        if (hasError) {
            createAlert(hasError, message);
            return;
        }
    }

    private Boolean verifyFields() throws BlankFieldsException, InvalidSelectionDaysException{
        verifyFieldsIsEmpty();
        verifyDaysSelected();
        return true;
    }

    private Boolean verifyFieldsIsEmpty() throws BlankFieldsException{
        if(txtNumbersOfPeople.getText().isEmpty()   ||
           txtDepartureHour.getText().isEmpty()     ||
           txtDepartureLocation.getText().isEmpty() ||
           txtArriveLocation.getText().isEmpty()    ||
           txtDescription.getText().isEmpty()       ||
           txtTitle.getText().isEmpty()){
        
           throw new BlankFieldsException("one or more fields is blank");
        }

        return true;
    }

    private Boolean verifyDaysSelected() throws InvalidSelectionDaysException{
        Boolean hasChecked = false;
        for(Boolean check : checkDaysMap.values()){
            if(check){
                hasChecked = true;
            }
        }
        if(!hasChecked){
            throw new InvalidSelectionDaysException("one day of the week must be selected."); 
        }
        return true;
    }

    private void createAlert(Boolean hasError, String message) throws IOException {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText("Status de Criação de POST");
        alert.setContentText(!hasError ? "Criado com Sucesso" : message);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK && hasError.equals(false)) {
            App.setRoot("loginScreen");
        }
    }
}

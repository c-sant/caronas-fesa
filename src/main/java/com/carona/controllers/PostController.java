package com.carona.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.carona.App;
import com.carona.exceptions.BlankFieldsException;
import com.carona.exceptions.EntityAlreadyExistsException;
import com.carona.exceptions.InvalidSelectionDaysException;
import com.carona.models.AvailableWeekdaysModel;
import com.carona.models.LocationModel;
import com.carona.models.PostModel;
import com.carona.services.PostService;

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
    private void onCreatePost() throws IOException, BlankFieldsException, InvalidSelectionDaysException, EntityAlreadyExistsException, SQLException {
        Boolean hasError = false;
        String message = "";
        try{
            verifyFields();
        }catch(BlankFieldsException err){
            hasError = true;
            message = "Ainda existe campos obrigat??rios n??o preenchidos";
        }catch(InvalidSelectionDaysException err){
            hasError = true;
            message = "Pelo menos um dia deve ser selecionado";
        }

        if (hasError) {
            createAlert(hasError, message);
            return;
        }
        
        PostService service = new PostService();
        String response1 = service.getLatAndLon(txtDepartureLocation.getText());
        String response2 = service.getLatAndLon(txtArriveLocation.getText());
        String lon1 = response1.split(",")[0];
        String lat1 = response1.split(",")[1];
        
        String lon2 = response2.split(",")[0];
        String lat2 = response2.split(",")[1];
        PostModel model = new PostModel(
                -1, 
                App.getUser(),
                txtTitle.getText(), 
                txtDescription.getText(), 
                new LocationModel(-1, Double.valueOf(lat1), Double.valueOf(lon1)),
                new LocationModel(-1,Double.valueOf(lat2), Double.valueOf(lon2)),
                new AvailableWeekdaysModel(-1,false, checkDaysMap.get("chkMonday"), checkDaysMap.get("chkTuesday"), checkDaysMap.get("chkWednesday"), 
                checkDaysMap.get("chkThursday"), checkDaysMap.get("chkFriday"), checkDaysMap.get("chkSaturday")),
                Integer.valueOf(txtNumbersOfPeople.getText()), 
                LocalTime.parse(txtDepartureHour.getText()),
                LocalDateTime.now()
            );

       
        service.create(model);
        createAlert(hasError, "Criado com Sucesso");
        App.setRoot("mainScreen");
        
    }


    @FXML void onCancel() throws IOException{
        App.setRoot("mainScreen");
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
        alert.setHeaderText("Status de Cria????o de POST");
        alert.setContentText(!hasError ? "Criado com Sucesso" : message);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK && hasError.equals(false)) {
            App.setRoot("loginScreen");
        }
    }
}

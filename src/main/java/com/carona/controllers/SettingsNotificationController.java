package com.carona.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import com.carona.App;
import com.carona.exceptions.BlankFieldsException;
import com.carona.exceptions.EntityAlreadyExistsException;
import com.carona.exceptions.EntityDoesNotExistException;
import com.carona.models.AvailableWeekdaysModel;
import com.carona.models.LocationModel;
import com.carona.models.NotificationConfigModel;
import com.carona.services.NotificationConfigService;
import com.carona.services.NotificationService;
import com.carona.services.PostService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SettingsNotificationController {
    

    @FXML
    Button btnOnSave;

    @FXML
    Button btnOnCancel;

    @FXML
    TextField txtLocale;

    @FXML
    TextField txtDistance;
    
    @FXML
    TextField txtIntervalInitial;

    @FXML
    TextField txtIntervalEnd;
    
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
    TextField chkWednesday;

    @FXML
    TextField chkYes;

    @FXML
    TextField chkNo;

    Map<String, Boolean> checkDaysMap = new HashMap<String, Boolean>();
    NotificationConfigService notificationService = new NotificationConfigService();
    NotificationConfigModel model = new NotificationConfigModel();

    @FXML
    public void initialize() throws IOException, MalformedURLException, BlankFieldsException, EntityAlreadyExistsException, EntityDoesNotExistException, SQLException {
        checkDaysMap.put("chkMonday"   , false);
        checkDaysMap.put("chkTuesday"  , false);
        checkDaysMap.put("chkWednesday", false);
        checkDaysMap.put("chkThursday" , false);
        checkDaysMap.put("chkFriday"   , false);
        checkDaysMap.put("chkSaturday" , false);
        checkDaysMap.put("chkYes" , false);
        checkDaysMap.put("chkNo" , false);
        chkMonday.setOnMouseClicked(event -> selectedOrUnselected((TextField) event.getSource()));
        chkTuesday.setOnMouseClicked(event -> selectedOrUnselected((TextField) event.getSource()));
        chkWednesday.setOnMouseClicked(event -> selectedOrUnselected((TextField) event.getSource()));
        chkThursday.setOnMouseClicked(event -> selectedOrUnselected((TextField) event.getSource()));
        chkFriday.setOnMouseClicked(event -> selectedOrUnselected((TextField) event.getSource()));
        chkSaturday.setOnMouseClicked(event -> selectedOrUnselected((TextField) event.getSource()));
        chkYes.setOnMouseClicked(event -> selectedOrUnselected((TextField) event.getSource()));
        chkNo.setOnMouseClicked(event -> selectedOrUnselected((TextField) event.getSource()));
        model = notificationService.readByUserId();
        loadInformation();
        
    }

    private void loadInformation(){
        if(model != null){
            // txtLocale.setText(model.getPlaceOfDeparture());
            txtDistance.setText(String.valueOf(model.getMaxDistanceInKm()));
            txtIntervalEnd.setText(String.valueOf(model.getFinalDepartureTime()));
            txtIntervalInitial.setText(String.valueOf(model.getInitialDepartureTime()));
            // if(model.getReceiveNotification().equals(true)){
            //     ((TextField) App.getNode("#chkYes")).getStyleClass().add("selected");
            // }else{
            //     ((TextField) App.getNode("#chkNo")).getStyleClass().add("selected");
            // }
        }
    }

    private void selectedOrUnselected(TextField txtEvent){
        String chkId = (String)txtEvent.getId();
        Boolean isSelected = !checkDaysMap.get(chkId);
        TextField txtField = (TextField) App.getNode("#" + chkId);
        
        if(chkId.equals("chkYes") || chkId.equals("chkNo")){
            TextField txtYes = (TextField) App.getNode("#chkYes" );
            TextField txtNo = (TextField) App.getNode("#chkNo");
            txtYes.getStyleClass().remove("selected");
            txtNo.getStyleClass().remove("selected");
            checkDaysMap.put("chkNo", false);
            checkDaysMap.put("chkYes", false);
        }
        
        if(isSelected){
            txtField.getStyleClass().add("selected");
        }else{
            txtField.getStyleClass().remove("selected");
        }
        checkDaysMap.put(chkId, isSelected);
   }

    @FXML 
    public void onClickSave() throws IOException, MalformedURLException, BlankFieldsException, EntityAlreadyExistsException, EntityDoesNotExistException, SQLException{
        
       
        AvailableWeekdaysModel daysChecked = new AvailableWeekdaysModel(-1, false, checkDaysMap.get("chkMonday"), checkDaysMap.get("chkTuesday"), checkDaysMap.get("chkWednesday"), 
        checkDaysMap.get("chkThursday"), checkDaysMap.get("chkFriday"), checkDaysMap.get("chkSaturday"));
        
        PostService service = new PostService();
        String response = service.getLatAndLon(txtLocale.getText());
        LocationModel departure_place = new LocationModel(-1, Double.valueOf(response.split(",")[1]), Double.valueOf(response.split(",")[0])); // Preencher latitude e longitude conforme filtro feito
        
        NotificationConfigModel notification = 
        new NotificationConfigModel(model != null ? model.getId() : -1, 
        App.getUser(), 
        checkDaysMap.get("chkYes").equals(true) ? true : false, 
        departure_place, 
        Double.valueOf(txtDistance.getText()),
        daysChecked, 
        LocalTime.parse(txtIntervalInitial.getText()) , 
        LocalTime.parse(txtIntervalEnd.getText()));

        notificationService.create(notification);
        
    }
}

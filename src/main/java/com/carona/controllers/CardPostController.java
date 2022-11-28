package com.carona.controllers;

import java.util.HashMap;
import java.util.Map;

import com.carona.App;
import com.carona.models.PostModel;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CardPostController {
    PostModel post;
    
    public CardPostController() {
    }

    public CardPostController(PostModel post) {
        this.post = post;
    }

    @FXML
    Text txtTitle;
    
    @FXML
    Text txtHour;

    @FXML
    Rectangle rec1;

    @FXML
    Rectangle rec2;

    @FXML
    Rectangle rec3;
    
    @FXML
    Rectangle rec4;

    @FXML
    Rectangle rec5;

    @FXML
    Rectangle rec6;
    
    
    Map<Integer, Rectangle> checkAvailableSpotsMap = new HashMap<Integer, Rectangle>();
    @FXML
    public void initialize() {
        txtTitle.setText(post.getTitle());
        txtHour.setText(post.getCreatedTime().toLocalDate().toString());
        checkAvailableSpotsMap.put(1, rec1);
        checkAvailableSpotsMap.put(2, rec2);
        checkAvailableSpotsMap.put(3, rec3);
        checkAvailableSpotsMap.put(4, rec4);
        checkAvailableSpotsMap.put(5, rec5);
        checkAvailableSpotsMap.put(6, rec6);
        setAvailableSpots();
    }

    private void setAvailableSpots() {
        for(Integer i = 1; i <= post.getAvailableSeats(); i++){
            checkAvailableSpotsMap.get(i).setFill(Color.rgb(79, 248, 123));
        }
    }
}

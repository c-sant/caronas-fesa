package com.carona.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.carona.App;
import com.carona.models.PostModel;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ScreenController {

    @FXML
    Pane panePrincipal;

    @FXML
    Pane paneHeader;

    @FXML
    ImageView btnHome;

    @FXML
    ImageView btnSettings;

    @FXML
    ImageView btnAddPost;

    @FXML
    TextField txtSearch;

    @FXML
    TextField txtLocale;

    @FXML
    TextField txtDays;

    @FXML
    public void initialize() throws IOException {
        panePrincipal.setVisible(false);
        // paneHeader.getChildren().add(App.loadFXML("mainHeaderScreen"));
    }

    @FXML
    private void onClickSettings() throws IOException {
        resetPosition();
        panePrincipal.setVisible(true);
        panePrincipal.getChildren().add(App.loadFXML("settingsScreen"));
    }

    @FXML
    private void onClickHome() throws IOException {
        panePrincipal.setVisible(true);
        txtDays.setVisible(true);
        txtLocale.setVisible(true);
        txtSearch.setVisible(true);
        panePrincipal.getChildren().clear();
        panePrincipal.setPrefHeight(270);
        panePrincipal.setLayoutY(150);
        panePrincipal.getChildren().add(setDataInVBox());
    }

    private void resetPosition(){
        panePrincipal.setPrefHeight(339);
        panePrincipal.setLayoutY(83);
        txtDays.setVisible(false);
        txtLocale.setVisible(false);
        txtSearch.setVisible(false);
    }

    private List<PostModel> getDataPost(){
        //Algum metodo service que ira buscar os posts;
        return new ArrayList<PostModel>();
    }

    private ScrollPane setDataInVBox() throws IOException{
        List<PostModel> postList = getDataPost();
        
        ScrollPane sPane = new ScrollPane();
        sPane.setPrefHeight(270);
        sPane.setPrefWidth(247.0);

        VBox cards = new VBox();
        cards.setPrefHeight(270);
        cards.setPrefWidth(230.0);
        cards.setStyle("-fx-background-color: transparent; -fx-padding: 15");
        cards.setSpacing(10);

        for(PostModel post : postList){
            cards.getChildren().add(App.loadFXML("cardPost", post));
        }
        cards.getChildren().add(App.loadFXML("cardPost"));
        cards.getChildren().add(App.loadFXML("cardPost"));
        cards.getChildren().add(App.loadFXML("cardPost"));
        cards.getChildren().add(App.loadFXML("cardPost"));
        sPane.setContent(cards);

        return sPane;
    }

    @FXML
    private void onClickUser() throws IOException {
        resetPosition();
        panePrincipal.setVisible(true);
        panePrincipal.getChildren().add(App.loadFXML("editUserScreen"));
        // paneHeader.getChildren().add(App.loadFXML("editUserHeaderScreen"));
    }

    @FXML 
    private void onClickCreatePost() throws IOException{
        resetPosition();
        panePrincipal.setVisible(true);
        panePrincipal.getChildren().add(App.loadFXML("postScreen"));
    }
 
}

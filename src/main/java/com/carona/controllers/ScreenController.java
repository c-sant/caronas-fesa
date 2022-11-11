package com.carona.controllers;

import java.io.IOException;

import com.carona.App;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

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
    public void initialize() throws IOException {
        panePrincipal.setVisible(false);
        paneHeader.getChildren().add(App.loadFXML("mainHeaderScreen"));
    }

    @FXML
    private void onClickSettings() throws IOException {
        panePrincipal.setVisible(true);
        panePrincipal.getChildren().add(App.loadFXML("settingsScreen"));
    }

    @FXML
    private void onClickHome() throws IOException {
        panePrincipal.setVisible(false);
        paneHeader.getChildren().add(App.loadFXML("mainHeaderScreen"));
    }

    @FXML
    private void onClickUser() throws IOException {
        panePrincipal.setVisible(true);
        panePrincipal.getChildren().add(App.loadFXML("editUserScreen"));
        paneHeader.getChildren().add(App.loadFXML("editUserHeaderScreen"));
    }
}

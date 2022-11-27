package com.carona.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carona.App;
import com.carona.models.AvailableWeekdaysModel;
import com.carona.models.LocationModel;
import com.carona.filters.AvailableWeekdaysFilter;
import com.carona.filters.LocationFilter;
import com.carona.filters.PostFilter;
import com.carona.models.PostModel;
import com.carona.services.NotificationService;
import com.carona.services.PostService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
    Text txtNotifications;

    @FXML
    TextField txtSearch;

    @FXML
    TextField txtLocale;

    @FXML
    TextField txtDistancia;

    @FXML
    CheckBox chkSeg;

    @FXML
    CheckBox chkTer;

    @FXML
    CheckBox chkQua;

    @FXML
    CheckBox chkQui;

    @FXML
    CheckBox chkSex;

    @FXML
    CheckBox chkSab;

    @FXML
    CheckBox chkDom;

    @FXML
    ImageView btnSearchPosts;

    PostService postService = new PostService();
    NotificationService notificationService = new NotificationService();

    private void setDafaultFilters() {
        txtSearch.setText("");
        txtLocale.setText("");

        fillCheckBox(chkSeg, true);
        fillCheckBox(chkTer, true);
        fillCheckBox(chkQua, true);
        fillCheckBox(chkQui, true);
        fillCheckBox(chkSex, true);
        fillCheckBox(chkSab, true);
        fillCheckBox(chkDom, true);
    }

    private void fillCheckBox(CheckBox checkBox, Boolean value) {
        if (checkBox.isSelected() != value) {
            checkBox.fire();
        }
    }

    

    @FXML
    public void initialize() throws IOException {
        panePrincipal.setVisible(false);

        setNotificationCount();
        setDafaultFilters();
        reloadPosts();

        // paneHeader.getChildren().add(App.loadFXML("mainHeaderScreen"));
    }

    private void setNotificationCount() {
        try {
            Integer notificationsCount = notificationService.countUserUnreadNotifications(App.getUser());
        
            txtNotifications.setText(notificationsCount.toString());
        } catch (SQLException ex) {
            System.out.println("Falha no carregamento dos posts");
        }
        
    }

    @FXML
    private void onClickSettings() throws IOException {
        resetPosition();
        panePrincipal.setVisible(true);
        panePrincipal.getChildren().add(App.loadFXML("settingsScreen"));
    }

    private void reloadPosts() throws IOException {
        panePrincipal.setVisible(true);
        txtLocale.setVisible(true);
        txtSearch.setVisible(true);
        panePrincipal.getChildren().clear();
        panePrincipal.setPrefHeight(270);
        panePrincipal.setLayoutY(150);
        panePrincipal.getChildren().add(setDataInVBox());
    }

    @FXML
    private void onClickHome() throws IOException {
        setDafaultFilters();
        reloadPosts();
        setNotificationCount();
    }

    private void resetPosition(){
        panePrincipal.setPrefHeight(339);
        panePrincipal.setLayoutY(83);
        txtLocale.setVisible(false);
        txtSearch.setVisible(false);
    }

    private void createFailToReadPostAlert() throws IOException {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Mensagem");
        alert.setHeaderText("Falha no carregamento!");
        alert.setContentText("Houve uma falha no carregamento dos posts. Tente novamente em instantes.");
        alert.showAndWait();
    }

    private PostFilter formatPostFilter() {
        AvailableWeekdaysModel availableWeekdaysModel = new AvailableWeekdaysModel(
            -1, chkDom.isSelected(), chkSeg.isSelected(), chkTer.isSelected(), chkQua.isSelected(), 
            chkQui.isSelected(), chkSex.isSelected(), chkSab.isSelected()
        );

        LocationModel departure_place = new LocationModel(-1, 2.3, 20.2); // Preencher latitude e longitude conforme filtro feito

        return new PostFilter(
                txtSearch.getText(),
                new AvailableWeekdaysFilter(availableWeekdaysModel),
                new LocationFilter(departure_place, Integer.parseInt(txtDistancia.getText()))
        );
    }

    private List<PostModel> getPosts() throws IOException {
        try {
            PostFilter postFilter = formatPostFilter();
            List<PostModel> posts = postService.readByAdvancedFilter(postFilter);

            return posts;
        } catch (SQLException ex) {
            createFailToReadPostAlert();
            return new ArrayList<PostModel>();
        }
    }

    @FXML
    private void onClickSearchPosts() throws IOException {
        reloadPosts();
        setNotificationCount();
    }

    private ScrollPane setDataInVBox() throws IOException {
        List<PostModel> postList = getPosts();
        
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

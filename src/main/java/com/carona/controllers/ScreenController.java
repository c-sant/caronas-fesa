package com.carona.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.carona.App;
import com.carona.DAO.NotificationDAO;
import com.carona.exceptions.BlankFieldsException;
import com.carona.exceptions.EntityAlreadyExistsException;
import com.carona.models.AvailableWeekdaysModel;
import com.carona.models.LocationModel;
import com.carona.models.NotificationConfigModel;
import com.carona.models.NotificationModel;
import com.carona.filters.AvailableWeekdaysFilter;
import com.carona.filters.LocationFilter;
import com.carona.filters.PostFilter;
import com.carona.models.PostModel;
import com.carona.models.UserModel;
import com.carona.services.NotificationConfigService;
import com.carona.services.NotificationService;
import com.carona.services.PostService;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ScreenController {

    @FXML
    Pane panePrincipal;

    @FXML
    Pane paneHeader;

    @FXML
    Pane panePopUp;

    @FXML
    Pane paneDisable;

    @FXML
    ImageView btnHome;

    @FXML
    ImageView btnSettings;

    @FXML
    ImageView btnAddPost;

    @FXML
    ImageView btnUser;

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

    @FXML
    Button btnFilterAdvanced;

    @FXML
    Button btnConfirmFilter;

    PostService postService = new PostService();
    NotificationService notificationService = new NotificationService();

    private void setDefaultFilters() {
        txtSearch.setText("");
        txtLocale.setText("");
        txtDistancia.setText("10");

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
        panePopUp.setVisible(false);
        paneDisable.setVisible(false);
        setNotificationCount();
        setDefaultFilters();
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
        btnFilterAdvanced.setVisible(true);
        panePrincipal.getChildren().clear();
        panePrincipal.setPrefHeight(270);
        panePrincipal.setLayoutY(150);
        panePrincipal.getChildren().add(setDataInVBox());
    }

    @FXML
    private void onClickHome() throws IOException, SQLException, BlankFieldsException, EntityAlreadyExistsException {
    //     PostService postService = new PostService();
    //     postService.create(
    //         new PostModel(
    //                 -1,
    //                 App.getUser(), 
    //                 "Título do post teste", 
    //                 "Descrição do post teste", 
    //                 new LocationModel(-1, 10.0, 10.0),
    //                 new LocationModel(-1, 10.00001, 10.00001),
    //                 new AvailableWeekdaysModel(-1, true, true, true, true, true, true, true), 
    //                 2,
    //                 LocalTime.now(), LocalDateTime.now()
    //             )
    //         );

    //     setDafaultFilters();
    // private void onClickHome() throws IOException {
        setDefaultFilters();
        reloadPosts();
        setNotificationCount();
    }

    private void resetPosition(){
        panePrincipal.setPrefHeight(339);
        panePrincipal.setLayoutY(83);
        txtLocale.setVisible(false);
        txtSearch.setVisible(false);
        btnFilterAdvanced.setVisible(false);
    }

    private void createFailToReadPostAlert() throws IOException {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Mensagem");
        alert.setHeaderText("Falha no carregamento!");
        alert.setContentText("Houve uma falha no carregamento dos posts. Tente novamente em instantes.");
        alert.showAndWait();
    }

    @FXML
    private void onNotificationsClicked()  {
        try {
            // Altera a visualização das recomendações, além de marcar as notificações como visto
            if (txtNotifications.getText() != "0") {
                notificationService.setUserNotificationsViewed(App.getUser());
                txtNotifications.setText("0");
            }

            // Captura as notificações
            List<NotificationModel> notifications = notificationService.readUserNotifications(App.getUser());

            // Faz a lógica necessária. Provavelmente dar um get em post por Id, pois ele não traz de forma automática.
            for (NotificationModel notificationModel : notifications) {
                System.out.println("Id da notificação = " + notificationModel.getId());
                System.out.println("Id do Post = " + notificationModel.getPost().getId());
                System.out.println("Data de criação = " + notificationModel.getNotificationTime());
                System.out.println("---------------------------------------");
            }

        } catch (SQLException ex) {
            System.out.println("Falha removendo notificações vistas");
        }
    }

    private PostFilter formatPostFilter() {
        AvailableWeekdaysModel availableWeekdaysModel = new AvailableWeekdaysModel(
            -1, chkDom.isSelected(), chkSeg.isSelected(), chkTer.isSelected(), chkQua.isSelected(), 
            chkQui.isSelected(), chkSex.isSelected(), chkSab.isSelected()
        );

        LocationModel departure_place = new LocationModel(-1, 10.0, 10.001); // Preencher latitude e longitude conforme filtro feito

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
        sPane.setId("paneCards");

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
 
    @FXML
    private void onClickFilterAdvanced() throws IOException{
        setDisableInComponents(true);
    }

    @FXML
    private void onClickFilterConfirm() throws IOException{
        setDisableInComponents(false);
    }

    private void setDisableInComponents(Boolean context){
        panePopUp.setVisible(context);
        paneDisable.setVisible(context);
        btnHome.setDisable(context);
        btnSettings.setDisable(context);
        btnAddPost.setDisable(context);
        btnUser.setDisable(context);
        ScrollPane sPane = (ScrollPane) App.getNode("#paneCards");
        sPane.setDisable(context);
    }

   
}

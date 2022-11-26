package com.carona;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.carona.controllers.PostController;
import com.carona.models.PostModel;
import com.carona.models.UserModel;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static UserModel user;

    public static UserModel getUser() {
        return user;
    }

    public static void setUser(UserModel userParameter) {
        user = userParameter;
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("loginScreen"), 300, 500);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("screens/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Parent loadFXML(String fxml, PostModel postModel) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("screens/" + fxml + ".fxml"));
        PostController controller = new PostController();
        fxmlLoader.setController(controller);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Node> T getNode(String id){
        final Node node = scene.lookup(id);
        return (T) node;
    }

}
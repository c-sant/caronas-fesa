package com.carona;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import com.carona.models.User;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static User user;

    public static User getUser(){
        return user;
    }

    public static void setUser(User userParameter){
        user = userParameter;
    }

    @Override
    public void start(Stage stage) throws IOException{
        scene = new Scene(loadFXML("loginScreen"), 300, 500);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource( "screens/"+ fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
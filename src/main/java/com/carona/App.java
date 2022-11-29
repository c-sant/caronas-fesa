package com.carona;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.carona.DAO.UserDAO;
import com.carona.controllers.CardPostController;
import com.carona.exceptions.BlankFieldsException;
import com.carona.exceptions.EntityAlreadyExistsException;
import com.carona.exceptions.EntityDoesNotExistException;
import com.carona.models.AvailableWeekdaysModel;
import com.carona.models.Course;
import com.carona.models.LocationModel;
import com.carona.models.NotificationConfigModel;
import com.carona.models.PostModel;
import com.carona.models.UserModel;
import com.carona.services.PostService;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static UserModel user;
    private static NotificationConfigModel model;

    public static NotificationConfigModel getNotificationConfig(){
        return model;
    }

    public static void setNotificationConfig(NotificationConfigModel notificationConfig){
        model = notificationConfig;
    }

    public static UserModel getUser() {
        return user;
    }

    public static void setUser(UserModel userParameter) {
        user = userParameter;
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException, BlankFieldsException, EntityAlreadyExistsException, EntityDoesNotExistException { 
        // UserDAO userDAO = new UserDAO();
        // UserModel user = userDAO.readById("081200007");
        // if (user == null) {
        //     user = new UserModel("081200007", "Guilherme", "Alguma coisa sobre mim", Course.ComputerEngineering, "(11) 98741-0155", "12345678", null);
        //     userDAO.insert(user);   
        // }
    
        // UserModel n_user = userDAO.readById("081200007"); 
    
        // PostModel model = new PostModel(
        //         -1, 
        //         n_user,
        //         "Post teste", 
        //         "Descrição para post teste", 
        //         new LocationModel(-1, 10.1, 20.2),
        //         new LocationModel(-1, 30.3, 40.3),
        //         new AvailableWeekdaysModel(-1, false, false, true, true, false, true, false),
        //         3, 
        //         LocalTime.now(),
        //         LocalDateTime.now()
        //     );

        // PostService service = new PostService();
        // service.create(model);

        // Integer newId = model.getId();
        // System.out.println("Id criado = " + newId);

        // model.setTitle("Título alterado");
        // model.getDestination().setLatitude(1.1);
        // model.getPlaceOfDeparture().setLatitude(2.2);
        // model.getAvailableWeekdays().setSunday(true);
        
        // service.update(model);

        // PostModel newModel = service.readById(newId);

        // System.out.println("Id = " + newModel.getId());
        // System.out.println("Id do criador = " + newModel.getCreator().getId());
        // System.out.println("Título = " + newModel.getTitle());
        // System.out.println("Destination latitude = " + newModel.getDestination().getLatitude());
        // System.out.println("Place of departure latitude = " + newModel.getPlaceOfDeparture().getLatitude());
        // System.out.println("Domingo = " + newModel.getAvailableWeekdays().getSunday());
        
        // for (PostModel iterable_element : service.readAll()) {
        //     System.out.println(iterable_element.getId());
        // }

        // //service.remove(newModel);
        // System.out.println("Após deletado = " + service.readById(newId));


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
        CardPostController cardPostController = new CardPostController(postModel);
        fxmlLoader.setController(cardPostController);
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
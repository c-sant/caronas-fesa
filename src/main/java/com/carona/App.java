package com.carona;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

import com.carona.DAO.AvailableWeekdaysDAO;
import com.carona.DAO.LocationDAO;
import com.carona.DAO.PostDAO;
import com.carona.controllers.PostController;
import com.carona.models.AvailableWeekdaysModel;
import com.carona.models.LocationModel;
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
    public void start(Stage stage) throws IOException, SQLException {
        // AvailableWeekdaysDAO dao = new AvailableWeekdaysDAO();
        // AvailableWeekdaysModel model = new AvailableWeekdaysModel(
        //     -1, true, true, false, false, true, false, true
        // );
        // dao.insert(model);
        // Integer newId = model.getId();
        // System.out.println("Id criado = " + newId);

        // model.setMonday(false);
        // model.setTuesday(true);
        // dao.update(model);

        // AvailableWeekdaysModel newModel = new AvailableWeekdaysModel();
        // newModel.setId(newId);
        // newModel = dao.readById(newModel);

        // System.out.println("Id lido = " + newModel.getId());
        // System.out.println("Segunda deve ser false = " + newModel.getMonday());
        // System.out.println("Terça deve ser true = " + newModel.getTuesday());
        
        // for (AvailableWeekdaysModel iterable_element : dao.readAll()) {
        //     System.out.println(iterable_element.getId());
        // }

        // dao.remove(newModel);
        // System.out.println("Após deletado = " + dao.readById(newModel));
        

        // LocationDAO dao = new LocationDAO();
        // LocationModel model = new LocationModel(-1, 10.2, 11.2);
        // dao.insert(model);
        // Integer newId = model.getId();
        // System.out.println("Id criado = " + newId);

        // model.setLatitude(9.2);
        // model.setLongitude(12.2);
        // dao.update(model);

        // LocationModel newModel = new LocationModel();
        // newModel.setId(newId);
        // newModel = dao.readById(newModel);

        // System.out.println("Id lido = " + newModel.getId());
        // System.out.println("Latitude deve ser 9.2 = " + newModel.getLatitude());
        // System.out.println("Longitude deve ser 12.2 = " + newModel.getLongitude());
        
        // for (LocationModel iterable_element : dao.readAll()) {
        //     System.out.println(iterable_element.getId());
        // }

        // dao.remove(newModel);
        // System.out.println("Após deletado = " + dao.readById(newModel));

        PostDAO dao = new PostDAO();
        PostModel model = new PostModel(
                -1, 
                "Post teste", 
                "Descrição para post teste", 
                new LocationModel(-1, 10.1, 20.2),
                new LocationModel(-1, 30.3, 40.3),
                new AvailableWeekdaysModel(-1, false, false, true, true, false, true, false),
                2, 
                LocalTime.now()
            );
        dao.insert(model);
        Integer newId = model.getId();
        System.out.println("Id criado = " + newId);

        model.setTitle("Título alterado");
        model.getDestination().setLatitude(1.1);
        model.getPlaceOfDeparture().setLatitude(2.2);
        model.getAvailableWeekdays().setSunday(true);
        
        dao.update(model);

        PostModel newModel = dao.readById(new PostModel(newId));

        System.out.println("Id = " + newModel.getId());
        System.out.println("Título = " + newModel.getTitle());
        System.out.println("Destination latitude = " + newModel.getDestination().getLatitude());
        System.out.println("Place of departure latitude = " + newModel.getPlaceOfDeparture().getLatitude());
        System.out.println("Domingo = " + newModel.getAvailableWeekdays().getSunday());
        
        for (PostModel iterable_element : dao.readAll()) {
            System.out.println(iterable_element.getId());
        }

        dao.remove(newModel);
        System.out.println("Após deletado = " + dao.readById(newModel));


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
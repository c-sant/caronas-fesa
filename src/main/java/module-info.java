module com.carona {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.carona to javafx.fxml;
    opens com.carona.controllers to javafx.fxml;

    exports com.carona;
}

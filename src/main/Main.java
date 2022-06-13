package main;

import database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;


public class Main extends Application {


    public static void main(String[] args) {
        JDBC.openConnection();
        //Locale.setDefault(new Locale("fr"));
        launch(args);
        JDBC.closeConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        primaryStage.setTitle("Appointments and Customer Manager Login");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }
}

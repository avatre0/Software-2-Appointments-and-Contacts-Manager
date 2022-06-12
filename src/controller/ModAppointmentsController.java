package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Utilites;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModAppointmentsController implements Initializable {
    public TextField titleBox;
    public TextField descriptionBox;
    public TextField locationBox;
    public TextField typeBox;
    public ComboBox<String> contactCombo;
    public ComboBox<String> startTimeCombo;
    public ComboBox<String> endTimeCombo;
    public ComboBox<Integer> customerIDCombo;
    public ComboBox<Integer> userIDCombo;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;

    Stage stage;
    Parent scene;

    public void saveButton(ActionEvent actionEvent) throws IOException {
        //Todo mod appointments
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void exitButton(ActionEvent actionEvent) throws IOException {
        if (Utilites.confirmDisplay("Exit", "Are you sture you want to exit. Changes will not be saved")){
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

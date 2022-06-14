package controller;

import database.DBAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointment;
import util.LogName;
import util.Utilities;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    public Label appointmentCount;
    public Button customerButton;
    public Button appointmentsButton;
    public Button reportsButton;
    public Button logoutButton;

    /**
     * Lambda Expression
     */
    LogName logName = () -> {
        return "login_activity.txt";
    };

    /**
     * Creates a new File from a lambda expression value
     * If file exists it doest do anything.
     */
    private void createFile(){
        try {
            File newFile = new File(logName.getfileName());
            newFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes to file a login failure with username and timestamp
     * retrieves filename from lambda expression
     */
    private void logoutSuccess(){
        try {
            FileWriter fileWriter = new FileWriter(logName.getfileName(),true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("Logout Success:  Timestamp: " + simpleDateFormat.format(date) +"\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void customerButton(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
        stage.setTitle("Customers");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void appointmentsButton(ActionEvent actionEvent) throws IOException{
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setTitle("Appointments");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void reportsButton(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage.setTitle("Reports");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Returns the prograrm to the login page
     * @param actionEvent button press
     * @throws IOException
     */
    public void logoutButton(ActionEvent actionEvent) throws IOException {
        if (Utilities.confirmDisplay("Logout", "Are you sure you want to Logout?")) {
            createFile();
            logoutSuccess();
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            stage.setTitle("Appointments and Customer Manager");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    private void upcomingAppointmentAlert() throws SQLException {
        ObservableList<Appointment> appointmentsList = DBAppointment.getAllAppointments();
        LocalDateTime checkTime = LocalDateTime.now();
        int upcomingAppointmentCount = 0;

        if (appointmentsList != null) {
            for (Appointment appointment : appointmentsList) {
                LocalDateTime appointmentTime = appointment.getStartTime();
                if (appointmentTime.isBefore(checkTime.plusMinutes(15))) {
                    if (appointmentTime.isAfter(checkTime)) {
                        String appointmentString ="Appointment ID: " + Integer.toString(appointment.getId()) + " Appointment Start Time: " + appointment.getStartTime().toString();
                        Utilities.informationDisplay("Upcoming Appointment", appointmentString);
                        upcomingAppointmentCount++;
                    }
                }
            }
        }
        if (upcomingAppointmentCount != 0){
            appointmentCount.setText("Number of Upcoming Appointments: " + upcomingAppointmentCount);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            upcomingAppointmentAlert();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

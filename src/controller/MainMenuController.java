package controller;

import database.DBAppointment;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Main menu controller handles opening up other pages
 * Also, displaying an upcoming appointment alert
 */
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    public Label appointmentCount;
    public Button customerButton;
    public Button appointmentsButton;
    public Button reportsButton;
    public Button logoutButton;

    /**
     * Lambda Expression #2
     * This efficiently and with minimal code sets the string for what the log will be called
     */
    LogName logName = () -> "login_activity.txt";

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

    /**
     * Opens the Customer page
     * @param actionEvent Customer button
     */
    public void customerButton(ActionEvent actionEvent) {
        try {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
            stage.setTitle("Customers");
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the Appointment Page
     * @param actionEvent appointment button
     */
    public void appointmentsButton(ActionEvent actionEvent) {
        try {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
            stage.setTitle("Appointments");
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the reports page
     * @param actionEvent report button
     */
    public void reportsButton(ActionEvent actionEvent) {
        try {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
            stage.setTitle("Reports");
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the prograrm to the login page
     * @param actionEvent logout button press
     */
    public void logoutButton(ActionEvent actionEvent) {
        if (Utilities.confirmDisplay("Logout", "Are you sure you want to Logout?")) {
            createFile();
            logoutSuccess();
            try {
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
                stage.setTitle("Appointments and Customer Manager");
                stage.setScene(new Scene(scene));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Displays a message if there is a appointment within 15 minutes of opening the page
     */
    private void upcomingAppointmentAlert() {
        ObservableList<Appointment> appointmentsList = DBAppointment.getAllAppointments();
        LocalDateTime checkTime = LocalDateTime.now();
        int upcomingAppointmentCount = 0;

        if (appointmentsList != null) {
            for (Appointment appointment : appointmentsList) {
                LocalDateTime appointmentTime = appointment.getStartTime();
                if (appointmentTime.isBefore(checkTime.plusMinutes(15))) {
                    if (appointmentTime.isAfter(checkTime)) {
                        String appointmentString ="Appointment ID: " + appointment.getId() + " Appointment Start Time: " + appointment.getStartTime().toString();
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

    /**
     * Initializes the Main mneu page
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        upcomingAppointmentAlert();
    }
}

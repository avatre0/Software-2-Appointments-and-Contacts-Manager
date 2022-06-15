package controller;

import database.DBAppointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import util.Utilities;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller for the Appointments FXML. Displays appointments and allows for viewing next months, and next weeks
 * appointments.
 */
public class AppointmentsController implements Initializable {
    public TableColumn<Appointment,Integer> allApptIDCol;
    public TableColumn<Appointment,String> allTitleCol;
    public TableColumn<Appointment,String> allDescriptionCol;
    public TableColumn<Appointment,String> allLocationCol;
    public TableColumn<Appointment,String> allContactCol;
    public TableColumn<Appointment,String> allTypeCol;
    public TableColumn<Appointment, LocalDateTime> allStartTimeCol;
    public TableColumn<Appointment, LocalDateTime> allEndTimeCol;
    public TableColumn<Appointment, Integer> allCustIDCol;
    public TableColumn<Appointment, Integer> allUserIDCol;
    public TableColumn<Appointment, Integer> monthApptIDCol;
    public TableColumn<Appointment,String> monthTitleCol;
    public TableColumn<Appointment,String> monthDescriptionCol;
    public TableColumn<Appointment,String> monthLocationCol;
    public TableColumn<Appointment,String> monthContactCol;
    public TableColumn<Appointment,String> monthTypeCol;
    public TableColumn<Appointment, LocalDateTime>  monthStartTimeCol;
    public TableColumn<Appointment, LocalDateTime>  monthEndTimeCol;
    public TableColumn<Appointment,Integer> monthCustIDCol;
    public TableColumn<Appointment,Integer> monthUserIDCol;
    public TableColumn<Appointment,Integer> weekApptIDCol;
    public TableColumn<Appointment,String> weekTitleCol;
    public TableColumn<Appointment,String> weekDescriptionCol;
    public TableColumn<Appointment,String> weekLocationCol;
    public TableColumn<Appointment,String> weekContactCol;
    public TableColumn<Appointment,String> weekTypeCol;
    public TableColumn<Appointment, LocalDateTime>  weekStartTimeCol;
    public TableColumn<Appointment, LocalDateTime>  weekEndTimeCol;
    public TableColumn<Appointment,Integer> weekCustIDCol;
    public TableColumn<Appointment,Integer> weekUserIDCol;
    public TableView<Appointment> allAppointmentTable;
    public TableView<Appointment> monthAppointmentTable;
    public TableView<Appointment> weekAppointmentTable;
    public Tab AllTab;
    public Tab MonthTab;
    public Tab WeekTab;

    Stage stage;
    Parent scene;

    /**
     * Button for Create new Appointment action
     * Launches the CreateAppointment fxml
     * @param actionEvent button press event
     */
    public void createAppointment(ActionEvent actionEvent){
        try {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CreateAppointment.fxml"));
            stage.setTitle("Create Appointment");
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the edit appointment fxml
     * Passes the selected appointment from the selected table to be edited
     * @param actionEvent button press
     */
    public void editAppointment(ActionEvent actionEvent) {

        try {
            Appointment selectedAppointment = null;
            //Try to pass selected appointment to the mod Appointment controller
            if (AllTab.isSelected() && allAppointmentTable.getSelectionModel().getSelectedItem() != null) {
                // pass all table appointment
                selectedAppointment = allAppointmentTable.getSelectionModel().getSelectedItem();
                ModAppointmentsController.incomingAppointment(selectedAppointment);
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Update Appointment");
                stage.show();
            } else if (MonthTab.isSelected() && monthAppointmentTable.getSelectionModel().getSelectedItem() != null) {
                //pass month table appointment
                selectedAppointment = monthAppointmentTable.getSelectionModel().getSelectedItem();
                ModAppointmentsController.incomingAppointment(selectedAppointment);
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Update Appointment");
                stage.show();
            } else if (WeekTab.isSelected() && weekAppointmentTable.getSelectionModel().getSelectedItem() != null) {
                //pass week Appointment Table
                selectedAppointment = weekAppointmentTable.getSelectionModel().getSelectedItem();
                ModAppointmentsController.incomingAppointment(selectedAppointment);
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Update Appointment");
                stage.show();
            } else {
                Utilities.errorDisplay("Error", "Please make a Appointment Selection");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the selected appointment
     * @param actionEvent button delete appointment is clicked
     */
    public void deleteAppointment(ActionEvent actionEvent) {
        Appointment selectedAppointment = null;
        //Try to pass selected appointment to the mod Appointment controller
        if (AllTab.isSelected() && allAppointmentTable.getSelectionModel().getSelectedItem() != null) {
            // pass all table appointment
            selectedAppointment = allAppointmentTable.getSelectionModel().getSelectedItem();
        } else if (MonthTab.isSelected() && monthAppointmentTable.getSelectionModel().getSelectedItem() != null) {
            //pass month table appointment
            selectedAppointment = monthAppointmentTable.getSelectionModel().getSelectedItem();
        } else if (WeekTab.isSelected() && weekAppointmentTable.getSelectionModel().getSelectedItem() != null) {
            //pass week Appointment Table
             selectedAppointment = monthAppointmentTable.getSelectionModel().getSelectedItem();
        } else {
            Utilities.errorDisplay("Error", "Please make a Appointment Selection");
            return;
        }
        // Trys to delete appointment
        if (Utilities.confirmDisplay("Warning","Are you sure you want to delete this appointment")) {
            int id = selectedAppointment.getId();
            String type = selectedAppointment.getType();
            if (DBAppointment.deleteAppointment(selectedAppointment)) {
                Utilities.informationDisplay("Success", "Deletion of appointment ID: " + id + "Type: " + type + "was a success.");
            } else {
                Utilities.informationDisplay("Error", "Deletion of Appointment failed.");
            }
            //Updates the observable lists
            ObservableList<Appointment> allAppointments = DBAppointment.getAllAppointments();
            ObservableList<Appointment> monthAppointments = DBAppointment.getMonthAppointments();
            ObservableList<Appointment> weekAppointments = DBAppointment.getWeekAppointments();
            allAppointmentTable.setItems(allAppointments);
            monthAppointmentTable.setItems(monthAppointments);
            weekAppointmentTable.setItems(weekAppointments);
        }
    }

    /**
     * Returns to the main menu
     * @param actionEvent button press main menu
     */
    public void mainMenu(ActionEvent actionEvent){
        try {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setTitle("Appointments and Customer Manager");
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * initializes the appointments page
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //gets the observable lists for all,month,week
        ObservableList<Appointment> allAppointments = DBAppointment.getAllAppointments();
        ObservableList<Appointment> monthAppointments = DBAppointment.getMonthAppointments();
        ObservableList<Appointment> weekAppointments = DBAppointment.getWeekAppointments();

        //sets the all table in the all tab
        allAppointmentTable.setItems(allAppointments);

        allApptIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        allDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        allLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        allContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        allTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        allStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        allEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        allCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        allUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        //sets the month table in the month tab
        monthAppointmentTable.setItems(monthAppointments);

        monthApptIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        monthTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        monthDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        monthLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        monthContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        monthTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        monthEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        monthCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        monthUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        //sets the week table in the week tab
        weekAppointmentTable.setItems(weekAppointments);

        weekApptIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        weekTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        weekDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        weekLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        weekContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        weekTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        weekStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        weekEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        weekCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        weekUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }
}

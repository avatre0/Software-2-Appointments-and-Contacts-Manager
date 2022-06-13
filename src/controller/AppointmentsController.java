package controller;

import database.DBAppointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    Stage stage;
    Parent scene;

    public void createAppointment(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CreateAppointment.fxml"));
        stage.setTitle("Create Appointment");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void editAppointment(ActionEvent actionEvent) throws IOException {
        //Try to pass selected appointment to the mod Appointment controller
        if (allAppointmentTable.getSelectionModel().getSelectedItem() != null) {
            // pass all table appointment
            Appointment selectedAppointment = allAppointmentTable.getSelectionModel().getSelectedItem();
            ModAppointmentsController.incomingAppointment(selectedAppointment);
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Update Appointment");
            stage.show();
        } else if (monthAppointmentTable.getSelectionModel().getSelectedItem() != null) {
            //pass month table appointment
            Appointment selectedAppointment = monthAppointmentTable.getSelectionModel().getSelectedItem();
            ModAppointmentsController.incomingAppointment(selectedAppointment);
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Update Appointment");
            stage.show();
        } else if (weekAppointmentTable.getSelectionModel().getSelectedItem() != null) {
            //pass week Appointment Table
            Appointment selectedAppointment = monthAppointmentTable.getSelectionModel().getSelectedItem();
            ModAppointmentsController.incomingAppointment(selectedAppointment);
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Update Appointment");
            stage.show();
        } else {
            Utilities.errorDisplay("Error", "Please make a Appointment Selection");
        }
    }

    public void deleteAppointment(ActionEvent actionEvent) {
        //Todo Setup Delete
    }

    public void mainMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setTitle("Appointments and Customer Manager");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void allSelectedTab(Event event) {
        monthAppointmentTable.getSelectionModel().clearSelection();
        weekAppointmentTable.getSelectionModel().clearSelection();
    }

    public void monthSelectedTab(Event event) {
        allAppointmentTable.getSelectionModel().clearSelection();
        weekAppointmentTable.getSelectionModel().clearSelection();
    }

    public void weekSelectedTab(Event event) {
        allAppointmentTable.getSelectionModel().clearSelection();
        monthAppointmentTable.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Appointment> allAppointments = DBAppointment.getAllAppointments();
            ObservableList<Appointment> monthAppointments = DBAppointment.getMonthAppointments();
            ObservableList<Appointment> weekAppointments = DBAppointment.getWeekAppointments();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

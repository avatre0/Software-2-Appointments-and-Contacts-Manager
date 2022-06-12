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
    public TableColumn<Appointment,Integer> monthApptCol;
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
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void editAppointment(ActionEvent actionEvent) throws IOException {
        //Todo Pass selected Appointment
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void deleteAppointment(ActionEvent actionEvent) {
        //Todo Setup Delete
    }

    public void mainMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void allSelectedTab(Event event) {
    }

    public void monthSelectedTab(Event event) {
    }

    public void weekSelectedTab(Event event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Todo Load data into tables
        try {
            ObservableList<Appointment> allAppointments = DBAppointment.getAllAppointments();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

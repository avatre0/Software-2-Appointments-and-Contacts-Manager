package controller;

import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import util.CustomerAppointmentCounts;
import util.TypeAppointment;
import util.YearMonthAppointmentCount;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Reports implements Initializable {
    public TableView<TypeAppointment> typeTable;
    public TableColumn<TypeAppointment, String> typeTableTypeCol;
    public TableColumn<TypeAppointment, Integer> typeTableAmountCol;
    public TableView<YearMonthAppointmentCount> monthTable;
    public TableColumn<YearMonthAppointmentCount,Integer> monthTableYearCol;
    public TableColumn<YearMonthAppointmentCount,String> monthTableMonthCol;
    public TableColumn<YearMonthAppointmentCount,Integer> monthTableAmountCol;
    public ComboBox<Integer> contactIDCombo;
    public TableView<Appointment> contactScheduleTable;
    public Label contactName;
    public TableColumn<Appointment, Integer> contactScheduleApptIDCol;
    public TableColumn<Appointment, String> contactScheduleTitleCol;
    public TableColumn<Appointment, String> contactScheduleTypeCol;
    public TableColumn<Appointment, String> contactScheduleDescCol;
    public TableColumn<Appointment, LocalDateTime> contactScheduleStartCol;
    public TableColumn<Appointment, LocalDateTime> contactScheduleEndCol;
    public TableColumn<Appointment, Integer> contactScheduleCustIDCol;
    public TableView<CustomerAppointmentCounts> customerTotalAptIDTable;
    public TableColumn<CustomerAppointmentCounts, Integer> customerIDNumberAppointmentsCol;
    public TableColumn<CustomerAppointmentCounts, Integer> custAppointmentsIDAmount;

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<TypeAppointment> typeAppointmentsList = DBTypeAppointment.getAppointmentTypeCountList();
            ObservableList<YearMonthAppointmentCount> yearMonthAppointmentCountList = DBYearMonthAppointmentCount.getYearMonthCountList();
            ObservableList<CustomerAppointmentCounts> customerAppointmentCountsList = DBCustomerAppointmentCounts.getCustomerAppointmentCounts();

            typeTable.setItems(typeAppointmentsList);
            typeTableTypeCol.setCellValueFactory(new PropertyValueFactory<>("typeName"));
            typeTableAmountCol.setCellValueFactory(new PropertyValueFactory<>("count"));

            monthTable.setItems(yearMonthAppointmentCountList);
            monthTableYearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
            monthTableMonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
            monthTableAmountCol.setCellValueFactory(new PropertyValueFactory<>("count"));

            customerTotalAptIDTable.setItems(customerAppointmentCountsList);
            customerIDNumberAppointmentsCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            custAppointmentsIDAmount.setCellValueFactory(new PropertyValueFactory<>("count"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContactIDCombo();
    }

    /**
     * Sets the User Id comboBox
     */
    private void setContactIDCombo() {
        ObservableList<Integer> contactList = FXCollections.observableArrayList();
        try {
            ObservableList<Contact> contacts = DBContact.getContactList();
            if (contacts != null) {
                for (Contact contact : contacts) {
                    contactList.add(contact.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        contactIDCombo.setItems(contactList);
    }

    public void typeMonthTab(Event event) {
    }

    public void contactScheduleTab(Event event) {
    }

    public void customerIDSelected(ActionEvent actionEvent) throws SQLException {
        int id = contactIDCombo.getSelectionModel().getSelectedItem();
        Contact contact = DBContact.getContactByID(id);
        ObservableList<Appointment> appointmentListByContact = DBAppointment.getAppointmentsByContact(contact.getId());

        if (contact.getId() != 0) {
            contactName.setText(contact.getName());
            contactScheduleTable.setItems(appointmentListByContact);
            contactScheduleApptIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            contactScheduleTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            contactScheduleTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            contactScheduleDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            contactScheduleStartCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            contactScheduleEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            contactScheduleCustIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        }
    }

    public void mainMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setTitle("Appointments and Customer Manager");
        stage.setScene(new Scene(scene));
        stage.show();
    }
}

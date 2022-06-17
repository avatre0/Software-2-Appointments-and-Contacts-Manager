package controller;

import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import util.ContactName;
import util.CustomerAppointmentCounts;
import util.TypeAppointment;
import util.YearMonthAppointmentCount;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller for the reports fxml
 */
public class ReportsController implements Initializable {
    public TableView<TypeAppointment> typeTable;
    public TableColumn<TypeAppointment, String> typeTableTypeCol;
    public TableColumn<TypeAppointment, Integer> typeTableAmountCol;
    public TableView<YearMonthAppointmentCount> monthTable;
    public TableColumn<YearMonthAppointmentCount,Integer> monthTableYearCol;
    public TableColumn<YearMonthAppointmentCount,String> monthTableMonthCol;
    public TableColumn<YearMonthAppointmentCount,Integer> monthTableAmountCol;
    public ComboBox<Integer> contactIDCombo;
    public TableView<Appointment> contactScheduleTable;
    public Label contactNameLabel;
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
    public TableColumn<CustomerAppointmentCounts, String> customerNameNumberAppointmentsCol;

    Stage stage;
    Parent scene;

    /**
     * Lambda Expression #3
     * This lambda gets and adds a space to the front of a contact name
     * This efficiently retrieve and movies the contact name, for better code reuse.
     */
    ContactName contactName = (Contact contact) -> " " + contact.getName();

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

    /**
     * When the Customer ID is selected it sets the table to show the correct appointment information for that tab
     * Calls a lambda expression to set the contact name when the combo box is set
     * @param actionEvent Combo selection
     */
    public void customerIDSelected(ActionEvent actionEvent) {
        int id = contactIDCombo.getSelectionModel().getSelectedItem();
        Contact contact = DBContact.getContactByID(id);
        ObservableList<Appointment> appointmentListByContact = DBAppointment.getAppointmentsByContact(contact.getId());

        if (contact.getId() != 0) {
            contactNameLabel.setText(contactName.getContactName(contact));
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

    /**
     * Returns to the main menu
      * @param actionEvent Main menu button
     */
    public void mainMenu(ActionEvent actionEvent) {
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
     * Initializes the Reports page
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Observable list generation for each tab
        ObservableList<TypeAppointment> typeAppointmentsList = DBTypeAppointment.getAppointmentTypeCountList();
        ObservableList<YearMonthAppointmentCount> yearMonthAppointmentCountList = DBYearMonthAppointmentCount.getYearMonthCountList();
        ObservableList<CustomerAppointmentCounts> customerAppointmentCountsList = DBCustomerAppointmentCounts.getCustomerAppointmentCounts();

        // Setting the values for each table
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
        customerNameNumberAppointmentsCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        setContactIDCombo();
    }
}

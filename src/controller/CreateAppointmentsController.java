package controller;

import database.DBAppointment;
import database.DBContact;
import database.DBCustomer;
import database.DBUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import util.Utilities;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class CreateAppointmentsController implements Initializable {
    public TextField titleBox;
    public TextField descriptionBox;
    public TextField locationBox;
    public TextField typeBox;
    public ComboBox<String> contactCombo;
    public ComboBox<LocalTime> startTimeCombo;
    public ComboBox<LocalTime> endTimeCombo;
    public ComboBox<Integer> customerIDCombo;
    public ComboBox<Integer> userIDCombo;
    public DatePicker startDatePicker;

    Stage stage;
    Parent scene;

    /**
     * Saves Appointee information to the database
     * @param actionEvent Save Button Press
     * @throws IOException  Catches and displays an error if cant save
     * @throws SQLException Catches and displays an error if cant save
     */
    public void saveButton(ActionEvent actionEvent) throws IOException, SQLException {
        if (checkIfNotEmpty()) {
            if (Utilities.confirmDisplay("Save", "Are You sure you want to save this Appointment?")) {
                if (checkForNoOverlapAppointment()) {
                    if (startEndCheck()) {
                        String title = titleBox.getText();
                        String description = descriptionBox.getText();
                        String location = locationBox.getText();
                        String type = typeBox.getText();
                        String contactName = contactCombo.getValue();
                        LocalTime startTime = startTimeCombo.getValue();
                        LocalTime endTime = endTimeCombo.getValue();
                        int contactID = DBContact.getContactID(contactName);
                        int customerID = customerIDCombo.getValue();
                        int userID = userIDCombo.getValue();
                        LocalDate startDate = startDatePicker.getValue();

                        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                        LocalDateTime endDateTime = LocalDateTime.of(startDate, endTime);

                        Appointment newAppointment = new Appointment(0, title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID, contactName);
                        try {
                            if (DBAppointment.createAppointment(newAppointment)) {
                                Utilities.informationDisplay("Successful", "Creation Successful. Returning to Appointments.");
                                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                                scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
                                stage.setScene(new Scene(scene));
                                stage.show();
                            } else {
                                Utilities.errorDisplay("Error", "Failed to Create Appointment");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Utilities.errorDisplay("Error", "End time cannot be before Start Time.");
                    }
                }
            }
        }
    }

    public void exitButton(ActionEvent actionEvent) throws IOException {
        if (Utilities.confirmDisplay("Exit", "Are you sture you want to exit. Changes will not be saved")){
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    private boolean checkIfNotEmpty() {
        if (titleBox.getText().isEmpty()) {
            Utilities.errorDisplay("Error", "Title is Required.");
            return false;
        }
        if (descriptionBox.getText().isEmpty()) {
            Utilities.errorDisplay("Error", "Title is Required.");
            return false;
        }
        if (locationBox.getText().isEmpty()) {
            Utilities.errorDisplay("Error", "Title is Required.");
            return false;
        }
        if (typeBox.getText().isEmpty()) {
            Utilities.errorDisplay("Error", "Title is Required.");
            return false;
        }
        if (contactCombo.getSelectionModel().isEmpty()) {
            Utilities.errorDisplay("Error", "Title is Required.");
            return false;
        }
        if (startTimeCombo.getSelectionModel().isEmpty()) {
            Utilities.errorDisplay("Error", "Title is Required.");
            return false;
        }
        if (endTimeCombo.getSelectionModel().isEmpty()) {
            Utilities.errorDisplay("Error", "Title is Required.");
            return false;
        }
        if (customerIDCombo.getSelectionModel().isEmpty()) {
            Utilities.errorDisplay("Error", "Title is Required.");
            return false;
        }
        if (userIDCombo.getSelectionModel().isEmpty()) {
            Utilities.errorDisplay("Error", "Title is Required.");
            return false;
        }
        if (startDatePicker.getValue() == null) {
            Utilities.errorDisplay("Error", "Title is Required.");
            return false;
        }

        return true;
    }

    private void setContactCombo() {
        ObservableList<String> contactList = FXCollections.observableArrayList();
        try {
            ObservableList<Contact> contacts = DBContact.getContactList();
            if(contacts != null) {
                for(Contact contact : contacts) {
                    contactList.add(contact.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        contactCombo.setItems(contactList);
    }

    /**
     * Sets the User Id comboBox
     */
    private void setUserIDCombo() {
        ObservableList<Integer> userList = FXCollections.observableArrayList();
        try {
            ObservableList<User> users = DBUser.getUserList();
            if (users != null) {
                for (User user : users) {
                    userList.add(user.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        userIDCombo.setItems(userList);
    }

    private void setCustomerIDCombo() {
        ObservableList<Integer> customerList = FXCollections.observableArrayList();
        try {
            ObservableList<Customer> customers = DBCustomer.getCustomers();
            if (customers != null) {
                for (Customer customer : customers) {
                    customerList.add(customer.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        customerIDCombo.setItems(customerList);
    }

    /**
     * Sets the time Combo boxes with local time equivalents of 8am EST to 10pm EST
     */
    private void setTimeCombos() {
        LocalTime openTime = LocalTime.of(8,0);
        LocalTime closeTime = LocalTime.of(22,0);
        ObservableList<LocalTime> timeList = FXCollections.observableArrayList();

        LocalTime openLocalTime = Utilities.estToLocal(LocalDateTime.of(startDatePicker.getValue(),openTime)).toLocalTime();
        LocalTime closeLocalTime = Utilities.estToLocal(LocalDateTime.of(startDatePicker.getValue(),closeTime)).toLocalTime();

        timeList.add(openLocalTime);
        while (openLocalTime.isBefore(closeLocalTime)) {
            openLocalTime = openLocalTime.plusMinutes(15);
            timeList.add(openLocalTime);
        }

        startTimeCombo.setItems(timeList);
        endTimeCombo.setItems(timeList);
        startTimeCombo.setDisable(false);
        endTimeCombo.setDisable(false);
    }

    /**
     * Checks if the end time is before the start time
     * @return returns false if end time is before the start time, returns true if end is after start
     */
    private boolean startEndCheck() {
        if (endTimeCombo.getValue().isBefore(startTimeCombo.getValue())) {
            return false;
        }
        return true;
    }

    /**
     * Checks if suggested appointment overlaps with another of the same customers appointments
     * @return Bool true if customer doesnt have operlap
     * @throws SQLException
     */
    private boolean checkForNoOverlapAppointment() throws SQLException {
        ObservableList<Appointment> custAppointments = DBAppointment.getAppointmentsByCustID(customerIDCombo.getValue());
        LocalDateTime pickedStartDateTime = LocalDateTime.of(startDatePicker.getValue(),startTimeCombo.getValue());
        LocalDateTime pickedEndDateTime = LocalDateTime.of(startDatePicker.getValue(),endTimeCombo.getValue());

        for (Appointment appointment : custAppointments) {
            if (appointment.getStartTime().isAfter(pickedStartDateTime) && appointment.getStartTime().isBefore(pickedEndDateTime)){
                Utilities.errorDisplay("Error", "Appointments cannot overlap. ");
                return false;
            }
            if (appointment.getEndTime().isAfter(pickedStartDateTime) && appointment.getEndTime().isBefore(pickedEndDateTime)){
                Utilities.errorDisplay("Error", "Appointments cannot overlap. ");
                return false;
            }
        }
        return true;
    }

    /**
     * After a start date is picked it updates the time comboboxes
     * @param actionEvent date picked
     */
    public void startSelected(ActionEvent actionEvent) {
        setTimeCombos();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { ;
        setContactCombo();
        setUserIDCombo();
        setCustomerIDCombo();
    }
}

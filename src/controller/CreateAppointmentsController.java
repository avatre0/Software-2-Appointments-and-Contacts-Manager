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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * The controller for create appointments fxml
 * handles creating appointments.
 */
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
     */
    public void saveButton(ActionEvent actionEvent) {
        if (checkIfNotEmpty()) { // check if all the attributes are filled out
            if (Utilities.confirmDisplay("Save", "Are You sure you want to save this Appointment?")) { // confirm if you want to save the appointment
                if (checkForNoOverlapAppointment()) { //check if the appointment over laps with an existing appointment
                    if (startEndCheck()) { //check if the end time comes after the start time
                        //Setting variables for the constructor
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

                        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime); //creating a local Date time
                        LocalDateTime endDateTime = LocalDateTime.of(startDate, endTime);

                        // New appointment construction
                        Appointment newAppointment = new Appointment(0, title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID, contactName);
                        try {
                            if (DBAppointment.createAppointment(newAppointment)) { // if we were able to make the appointment return to the appointments FXML
                                Utilities.informationDisplay("Successful", "Creation Successful. Returning to Appointments.");
                                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                                scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
                                stage.setTitle("Customers");
                                stage.setScene(new Scene(scene));
                                stage.show();
                            } else {
                                Utilities.errorDisplay("Error", "Failed to Create Appointment");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Utilities.errorDisplay("Error", "End time cannot be before Start Time.");
                    }
                }
            }
        }
    }

    /**
     * Exits to the appointment list
     * @param actionEvent exit button press
     */
    public void exitButton(ActionEvent actionEvent) {
        try {
            //asks if the user wants to exit
            if (Utilities.confirmDisplay("Exit", "Are you sture you want to exit. Changes will not be saved")) {
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
                stage.setTitle("Customers");
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if all required boxes are filled
     * Provides error if one box isn't filled
     * @return bool true if all boxes are filled
     */
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

    /**
     * Sets the combo box for the contacts
     */
    private void setContactCombo() {
        //Observable list for the contact's
        ObservableList<String> contactList = FXCollections.observableArrayList();
        //List of contact objects
        ObservableList<Contact> contacts = DBContact.getContactList();
        if(contacts != null) { // if the list is empty dont do this
            //loops through the contact objects and gets the name for each one
            for(Contact contact : contacts) {
                contactList.add(contact.getName()); // adds them to the observable list
            }
        }
        contactCombo.setItems(contactList); //Sets the combo box
    }

    /**
     * Sets the User Id comboBox
     */
    private void setUserIDCombo() {
        //empty List of user ids to display
        ObservableList<Integer> userList = FXCollections.observableArrayList();
        //gets a list of user objects
        ObservableList<User> users = DBUser.getUserList();
        if (users != null) {
            //loops though user list and gets the ids from them
            for (User user : users) {
                userList.add(user.getId());
            }
        }
        userIDCombo.setItems(userList); //sets the combo box
    }

    /**
     * Sets the Customer ID combo box
     */
    private void setCustomerIDCombo() {
        //Empty list of customer ID's to display
        ObservableList<Integer> customerList = FXCollections.observableArrayList();
        try {
            //gets a list of customer objects
            ObservableList<Customer> customers = DBCustomer.getCustomers();
            if (customers != null) {
                //loops through that list
                for (Customer customer : customers) {
                    customerList.add(customer.getId()); //adds the id to the list
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        customerIDCombo.setItems(customerList); //sets the combo box
    }

    /**
     * Sets the time Combo boxes with local time equivalents of 8am EST to 10pm EST
     */
    private void setTimeCombos() {
        //Sets a time of 8 and 10pm
        LocalTime openTime = LocalTime.of(8,0);
        LocalTime closeTime = LocalTime.of(22,0);
        ObservableList<LocalTime> timeList = FXCollections.observableArrayList();

        //converts thoes times from EST to Local times
        LocalTime openLocalTime = Utilities.estToLocal(LocalDateTime.of(startDatePicker.getValue(),openTime)).toLocalTime();
        LocalTime closeLocalTime = Utilities.estToLocal(LocalDateTime.of(startDatePicker.getValue(),closeTime)).toLocalTime();

        //builds a observable list of times in 15 minutes increments
        timeList.add(openLocalTime);
        while (openLocalTime.isBefore(closeLocalTime)) {
            openLocalTime = openLocalTime.plusMinutes(15);
            timeList.add(openLocalTime);
        }

        //sets the combo boxes
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
     * @return Bool true if customer doesn't have overlap
     */
    private boolean checkForNoOverlapAppointment() {
        ObservableList<Appointment> custAppointments = DBAppointment.getAppointmentsByCustID(customerIDCombo.getValue());
        LocalDateTime pickedStartDateTime = LocalDateTime.of(startDatePicker.getValue(), startTimeCombo.getValue());
        LocalDateTime pickedEndDateTime = LocalDateTime.of(startDatePicker.getValue(), endTimeCombo.getValue());

        for (Appointment appointment : custAppointments) {
            if (appointment.getStartTime().isAfter(pickedStartDateTime) && appointment.getStartTime().isBefore(pickedEndDateTime)) {
                Utilities.errorDisplay("Error", "Appointments cannot overlap. ");
                return false;
            }
            if (appointment.getEndTime().isAfter(pickedStartDateTime) && appointment.getEndTime().isBefore(pickedEndDateTime)) {
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

    /**
     * Initializes the Create Appointment controller
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //sets the combo boxes
        setContactCombo();
        setUserIDCombo();
        setCustomerIDCombo();

    }
}

package controller;

import database.DBAppointment;
import database.DBCustomer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import model.Customer;
import util.Utilites;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    public TableView<Customer> customerTable;
    public TableColumn<Customer, Integer> idCol;
    public TableColumn<Customer, String> nameCol;
    public TableColumn<Customer, String> addressCol;
    public TableColumn<Customer, String> postalCol;
    public TableColumn<Customer, String> phoneCol;
    public TableColumn<Customer, String> countryCol;
    public TableColumn<Customer, String> divisionCol;

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Customer> customers = DBCustomer.getCustomers();
            customerTable.setItems(customers);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
            divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Launches the add customer window
     * @param actionEvent Button press of add customer
     * @throws IOException if the fxml doesn't exist
     */
    public void addCustomer(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CreateCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Launches the update customer window
     * @param actionEvent Button press of update customer
     * @throws IOException if the fxml doesn't exist
     */
    public void modCustomer(ActionEvent actionEvent) throws IOException {
        //Try to pass selected product to updateCustomer Controller
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            UpdateCustomerController.incomingCustomer(selectedCustomer);
            try {
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/View/UpdateCustomer.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Update Customer");
                stage.show();
            }
        //Catch for if no customers are selected, produces an alert to select a customer
            catch (NullPointerException e){
                Utilites.errorDisplay("Error","Please make a customer selection.");
            }
        }
        else {
            Utilites.errorDisplay("Error","Please make a customer selection.");
        }
    }

    /**
     * Deletes selected customer
     * @param actionEvent button press
     * @throws SQLException
     */
    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if(customer != null){ //if there is a customer selected
            if(verifyCustomerAppointments(customer)) { //confirm if customer has appointments
                if (Utilites.confirmDisplay("Error", "Customer has appointments. Would you like to delete associated Appointments as well?")) {
                    if (DBAppointment.deleteAppointmentsByCustID(customer.getId()) && DBCustomer.deleteCustomer(customer)) {
                        Utilites.informationDisplay("Success", "Deletion of appointments and customer was a success.");
                    }
                }
            }else{
                if (Utilites.confirmDisplay("Confirmation", "Are you sure you want to delete selected Customer?")) {
                    DBCustomer.deleteCustomer(customer); //deletes customer
                    Utilites.informationDisplay("Success", "Deletion was a success.");
                }
            }
        }else{// if a part is not selected display an error asking the user to make a selection
            Utilites.errorDisplay("Error","Please make a Customer selection.");
        }
        ObservableList<Customer> customers = DBCustomer.getCustomers(); //updates customer list
        customerTable.setItems(customers); //refreshes table
    }

    /**
     * Verfiy if customer has appointments
     * @param customer
     * @return
     */
    public boolean verifyCustomerAppointments(Customer customer) {
        int id = customer.getId();
        try {
            ObservableList<Appointment> appointmentsList = DBAppointment.getAppointmentsByCustID(id);
            if (appointmentsList.isEmpty()){
                return false;
            }
            else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Return to the main menu
     * @param actionEvent
     * @throws IOException
     */
    public void mainMenu(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}

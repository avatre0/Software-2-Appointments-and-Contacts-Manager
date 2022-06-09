package controller;

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
    /**
     * Initializes the window, sets the table type, and search for customers in the database
     */
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

    public void addCustomer(ActionEvent actionEvent) {
    }

    public void modCustomer(ActionEvent actionEvent) {
    }

    /**
     * Deletes selected customer
     * @param actionEvent button press
     * @throws SQLException
     */
    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if(customer != null){ //if there is a customer selected
            if(verifyCustomerAppointments(customer)) { //confirm delete
                Utilites.errorDisplay("Error", "Customer has appointments. Please remove appointments before deleting customer");
            }else{
                DBCustomer.deleteCustomer(customer); //Deletes customer
            }
        }else{// if a part is not selected display an error asking the user to make a selection
            Utilites.errorDisplay("Error","Please make a part selection.");
        }
    }

    /**
     * Verfiy if customer has appointments
     * @param customer
     * @return
     */
    public boolean verifyCustomerAppointments(Customer customer) {
        return true;
        // todo return false if no appointments
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

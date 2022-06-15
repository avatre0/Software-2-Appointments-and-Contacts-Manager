package controller;

import database.DBCountry;
import database.DBCustomer;
import database.DBDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import util.Utilities;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Update Customer's controller
 * Handles display the customer to update and changing the data for the customer you want to update
 */
public class UpdateCustomerController implements Initializable {
    public TextField idBox;
    public TextField nameBox;
    public TextField addressBox;
    public TextField postalCodeBox;
    public TextField phoneBox;
    public ComboBox<String> countryPick;
    public ComboBox<String> divisionPick;

    private static Customer selectedCustomer;

    Stage stage;
    Parent scene;

    /**
     * Saves the info in the text box's to the selected customer
     * @param actionEvent Save button press
     */
    public void saveButton(ActionEvent actionEvent) {
        if (checkIfNotEmpty()) {
            if (Utilities.confirmDisplay("Confirm", "Are You sure you want to update this Customer?")) {

                int id = Integer.parseInt(idBox.getText());
                String name = nameBox.getText();
                String address = addressBox.getText();
                String postalCode = postalCodeBox.getText();
                String phone = phoneBox.getText();
                String division = divisionPick.getValue();
                String country = countryPick.getValue();
                int divID = DBDivision.getDivisionIDByName(division).getId();

                Customer modCustomer = new Customer(id,name,address,postalCode,phone,division,country,divID);
                try {
                    boolean created = DBCustomer.modCustomer(modCustomer);
                    if (created) {
                        Utilities.informationDisplay("Successful","Modification Successful. Returning to Customers.");
                        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
                        stage.setTitle("Customers");
                        stage.setScene(new Scene(scene));
                        stage.show();
                    } else {
                        Utilities.errorDisplay("Error", "Failed to Modify Customer");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Exit button, back to customers
     * @param actionEvent Exit button action
     */
    public void exitButton(ActionEvent actionEvent) {
        try {
            if (Utilities.confirmDisplay("Confirm", "Are you sture you want to exit. Changes will not be saved")) {
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
                stage.setTitle("Customers");
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the country combo box data
     */
    private void setCountryPick() {
        ObservableList<String> countryList = FXCollections.observableArrayList(); // Creates a new list
        try {
            ObservableList<Country> countries = DBCountry.getCountries();
            if (countries != null) {
                for (Country country : countries) {
                    countryList.add(country.getCountry());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        countryPick.setItems(countryList);
    }

    /**
     * Receives the selected customer from the customer table.
     * @param customer customer object
     */
    public static void incomingCustomer(Customer customer){
        selectedCustomer = customer;
    }

    /**
     * When a country is selected this builds a list to enable the division combo
     */
    @FXML
    private void countrySelected() {
        String country = countryPick.getValue();
        ObservableList<String> divisionsList = FXCollections.observableArrayList();

        try {
            ObservableList<Division> divisions = DBDivision.getDivisionsByCountry(DBCountry.getCountryIDByName(country));
            if (divisions != null) {
                for (Division division : divisions) {
                    divisionsList.add(division.getDivision());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        divisionPick.setItems(divisionsList);
        divisionPick.setDisable(false);
        setExampleText();
    }
    /**
     * Checks if inputs are empty
     * @return bool true if not empty and false if empty
     */
    private boolean checkIfNotEmpty() {
        if (nameBox.getText().isEmpty()){
            Utilities.errorDisplay("Error", "Name is Required.");
            return false;
        }
        if (addressBox.getText().isEmpty()){
            Utilities.errorDisplay("Error", "Address is Required.");
            return false;
        }
        if (postalCodeBox.getText().isEmpty()){
            Utilities.errorDisplay("Error", "Postal Code is Required.");
            return false;
        }
        if (phoneBox.getText().isEmpty()){
            Utilities.errorDisplay("Error", "Phone Number is Required.");
            return false;
        }
        if (countryPick.getSelectionModel().isEmpty()){
            Utilities.errorDisplay("Error", "Division Selection is Required.");
            return false;
        }
        if (divisionPick.getSelectionModel().isEmpty()){
            Utilities.errorDisplay("Error", "Country Select is Required.");
            return false;
        }
        return true;
    }

    /**
     * Sets the correct example text based on country
     */
    private void setExampleText() {
        String country = countryPick.getValue();
        if (country.equals("U.S")) {
            addressBox.setPromptText("123 ABC Street, White Plains");
        } else if (country.equals("UK")) {
            addressBox.setPromptText("123 ABC Street, Greenwich, London");
        } else if (country.equals("Canada")) {
            addressBox.setPromptText("123 ABC Street, Newmarket");
        }
        addressBox.setDisable(false);
    }

    /**
     * Initializes the Update Customer page
     * Sets the boxes to the selected customers value
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idBox.setText(Integer.toString(selectedCustomer.getId()));
        nameBox.setText(selectedCustomer.getName());
        addressBox.setText(selectedCustomer.getAddress());
        postalCodeBox.setText(selectedCustomer.getPostalCode());
        phoneBox.setText(selectedCustomer.getPhone());
        countryPick.getSelectionModel().select(selectedCustomer.getCountry());
        divisionPick.getSelectionModel().select(selectedCustomer.getDivision());
        setCountryPick();
        countrySelected();
    }
}

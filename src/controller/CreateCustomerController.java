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
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller for the Create Customer FXML
 * Handles the creation of customers
 */
public class CreateCustomerController implements Initializable {
    @FXML
    public TextField idBox;
    @FXML
    public TextField nameBox;
    @FXML
    public TextField addressBox;
    @FXML
    public TextField postalCodeBox;
    @FXML
    public TextField phoneBox;
    @FXML
    public ComboBox<String> countryPick;
    @FXML
    public ComboBox<String> divisionPick;

    Stage stage;
    Parent scene;

    /**
     * Sets the country combo box data
     */
    private void setCountryPick() {
        ObservableList<String> countryList = FXCollections.observableArrayList(); // Creates a new list
        try {
            ObservableList<Country> countries = DBCountry.getCountries();
            if (countries != null) {
                for ( Country country : countries){
                    countryList.add(country.getCountry());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        countryPick.setItems(countryList);
    }
    /**
     * Saves customer to database
     * @param actionEvent button press that saves customer
     * @throws SQLException handled in the DBCustomer.java
     */
    @FXML
    private void saveButton(ActionEvent actionEvent) throws SQLException {
       if (checkIfNotEmpty()) {
           if (Utilities.confirmDisplay("Confirm", "Are You sure you want to save this Customer?")) {
               String name = nameBox.getText();
               String address = addressBox.getText();
               String postalCode = postalCodeBox.getText();
               String phone = phoneBox.getText();
               String division = divisionPick.getValue();
               String country = countryPick.getValue();
               int divID = DBDivision.getDivisionIDByName(division).getId();

               Customer newCustomer = new Customer(0,name,address,postalCode,phone,division,country,divID);
               try {
                   if (DBCustomer.addCustomer(newCustomer)) {
                       Utilities.informationDisplay("Successful", "Creation Successful. Returning to Customers.");
                       stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                       scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
                       stage.setTitle("Customers");
                       stage.setScene(new Scene(scene));
                       stage.show();
                   } else {
                       Utilities.errorDisplay("Error", "Failed to Create Customer");
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       }
    }

    /**
     * returns to the customer list page
     * @param actionEvent button press exit
     */
    @FXML
    private void exitButton(ActionEvent actionEvent) {
        if (Utilities.confirmDisplay("Confirm", "Are you sture you want to exit. Changes will not be saved")) {
            try {
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
                stage.setTitle("Customers");
                stage.setScene(new Scene(scene));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
     * When a country is selected this builds a list to enable the divsion combobox
     */
    @FXML
    private void countrySelected() {
        String country = countryPick.getValue();
        ObservableList<String> divisionsList = FXCollections.observableArrayList();

        try {
            ObservableList<Division>  divisions = DBDivision.getDivisionsByCountry(DBCountry.getCountryIDByName(country));
            if (divisions != null) {
                for ( Division division : divisions){
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
     * Initializes the Create Customer fxml
     * Sets the country combo box
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCountryPick();
    }
}

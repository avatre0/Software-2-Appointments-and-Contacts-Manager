package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Helper class to interact with Customers in the DB
 */
public class DBCustomer {


    /**
     * Pulls customer list from database
     * @return ObservableList<Customer> empty if exception
     */
    public static ObservableList<Customer> getCustomers() {
        ObservableList<Customer> customersList = FXCollections.observableArrayList(); // Creats a new list

        String sql = "SELECT * FROM customers AS c INNER JOIN first_level_divisions AS d ON c.Division_ID = d.Division_ID INNER JOIN countries AS co ON co.Country_ID=d.COUNTRY_ID;";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //turns that's statement into a prepared Statements
            ResultSet rs = ps.executeQuery(); // run the statement and stores the results in a ResultSet
            // While loop to loop through all results
            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String password = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                String division = rs.getString("Division");
                String county = rs.getString("Country");
                int divisionID = rs.getInt("Division_ID");
                // Creates new user with info from database
                model.Customer customer = new model.Customer(id, name, password, postalCode, phone, division, county, divisionID);
                customersList.add(customer); // adds that to the observable list
            }
            return customersList;
        } catch (SQLException e) {
            e.printStackTrace();
            return customersList;
        }
    }

    /**
     * Adds provided customer to the database
     * @param customer Customer object
     */
    public static boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1,customer.getName());
            ps .setString(2,customer.getAddress());
            ps.setString(3,customer.getPostalCode());
            ps.setString(4,customer.getPhone());
            ps.setInt(5,customer.getDivisionID());

            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Modifies Customer in database
     * @param customer object to modify
     */
    public static boolean modCustomer(Customer customer) {
        String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhone());
            ps.setInt(5, customer.getDivisionID());
            ps.setInt(6, customer.getId());

            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Deletes Provided customer
     * @param customer customer to delete
     * @return bool if delete was completed
     */
    public static boolean deleteCustomer(Customer customer) {
        int id = customer.getId();
        String sql = "DELETE FROM customers WHERE Customer_ID=?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

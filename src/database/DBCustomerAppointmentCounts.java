package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.CustomerAppointmentCounts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Helper Class to retrieve Customer's appointment counts
 */
public class DBCustomerAppointmentCounts {
    /**
     * Gets a list of customer ID's with the count of how many appointments they have
     * @return Observable List of customer id's and the count of how many appointments they have.
     */
    public static ObservableList<CustomerAppointmentCounts> getCustomerAppointmentCounts() {
        ObservableList<CustomerAppointmentCounts> customerAppointmentCountsList = FXCollections.observableArrayList();
        String sql = "SELECT appointments.Customer_ID, COUNT(*), customers.Customer_Name FROM appointments INNER JOIN customers on appointments.Customer_ID = customers.Customer_ID GROUP By Customer_ID;";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                int count = rs.getInt("COUNT(*)");
                String name = rs.getString("Customer_Name");
                CustomerAppointmentCounts newCustomerAppointmentCounts = new CustomerAppointmentCounts(id, count,name);
                customerAppointmentCountsList.add(newCustomerAppointmentCounts);
            }
            return customerAppointmentCountsList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

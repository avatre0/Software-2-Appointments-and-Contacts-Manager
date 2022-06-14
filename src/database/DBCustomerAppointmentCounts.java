package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.CustomerAppointmentCounts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomerAppointmentCounts {
    /**
     * Gets a list of customer ID's with the count of how many appointments they have
     * @return Observable List of customer id's and the count of how many appointments they have.
     * @throws SQLException catches and returns null
     */
    public static ObservableList<CustomerAppointmentCounts> getCustomerAppointmentCounts() throws SQLException {
        ObservableList<CustomerAppointmentCounts> customerAppointmentCountsList = FXCollections.observableArrayList();
        String sql = "SELECT Customer_ID, COUNT(*) FROM appointments GROUP By Customer_ID;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        try{
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("Customer_ID");
                int count = rs.getInt("COUNT(*)");
                CustomerAppointmentCounts newCustomerAppointmentCounts = new CustomerAppointmentCounts(id,count);
                customerAppointmentCountsList.add(newCustomerAppointmentCounts);
            }
            return customerAppointmentCountsList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

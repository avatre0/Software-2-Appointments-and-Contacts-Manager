package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.TypeAppointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Helper Class to get a list of Types of a appointments and count them
 */
public class DBTypeAppointment {

    /**
     * Gets types of appointments and the count of occurrences
     * @return Observable List of types of appointments and their count
     */
    public static ObservableList<TypeAppointment> getAppointmentTypeCountList() {
        ObservableList<TypeAppointment> typeAppointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT Type, COUNT(Type) FROM appointments GROUP BY Type;";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("Type");
                int count = rs.getInt("COUNT(Type)");
                TypeAppointment newTypeAppointment = new TypeAppointment(type, count);
                typeAppointmentsList.add(newTypeAppointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeAppointmentsList;
    }
}

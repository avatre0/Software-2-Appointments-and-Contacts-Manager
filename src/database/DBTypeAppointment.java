package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.TypeAppointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTypeAppointment {

    public static ObservableList<TypeAppointment> getAppointmentTypeCountList() throws SQLException {
        ObservableList<TypeAppointment> typeAppointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT Type, COUNT(Type) FROM appointments GROUP BY Type;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        try{
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("Type");
                int count = rs.getInt("COUNT(Type)");
                TypeAppointment newTypeAppointment = new TypeAppointment(type,count);
                typeAppointmentsList.add(newTypeAppointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        };
        return typeAppointmentsList;
    }
}

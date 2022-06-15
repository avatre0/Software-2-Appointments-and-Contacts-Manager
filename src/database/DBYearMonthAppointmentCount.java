package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.YearMonthAppointmentCount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Helper Class to handle getting Year Month appointment counts
 */
public class DBYearMonthAppointmentCount {

    /**
     * Gets a list of appointments by year and month and a count of the occurrences.
     * @return Observable list of Year,Month, Occurrence counts
     */
    public static ObservableList<YearMonthAppointmentCount> getYearMonthCountList() {
        ObservableList<YearMonthAppointmentCount> yearMonthAppointmentCountList = FXCollections.observableArrayList();
       String sql =  "SELECT EXTRACT(Year from start) AS Year, MONTHNAME(start) AS Month, COUNT(start) FROM appointments GROUP BY Year;";
       try {
           PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

           ResultSet rs = ps.executeQuery();
           while (rs.next()) {
               int year = rs.getInt("Year");
               String month = rs.getString("Month");
               int count = rs.getInt("COUNT(start)");
               YearMonthAppointmentCount newYearMonthAppointmentCount = new YearMonthAppointmentCount(year, month, count);
               yearMonthAppointmentCountList.add(newYearMonthAppointmentCount);
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return yearMonthAppointmentCountList;
    }
}

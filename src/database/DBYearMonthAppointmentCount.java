package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.YearMonthAppointmentCount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBYearMonthAppointmentCount {

    public static ObservableList<YearMonthAppointmentCount> getYearMonthCountList() throws SQLException {
        ObservableList<YearMonthAppointmentCount> yearMonthAppointmentCountList = FXCollections.observableArrayList();
       String sql =  "SELECT EXTRACT(Year from start) AS Year, MONTHNAME(start) AS Month, COUNT(start) FROM appointments GROUP BY Year;";
       PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

       try {
           ResultSet rs = ps.executeQuery();
           while (rs.next()) {
               int year = rs.getInt("Year");
               String month = rs.getString("Month");
               int count = rs.getInt("COUNT(start)");
               YearMonthAppointmentCount newYearMonthAppointmentCount = new YearMonthAppointmentCount(year,month,count);
               yearMonthAppointmentCountList.add(newYearMonthAppointmentCount);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return yearMonthAppointmentCountList;
    }
}

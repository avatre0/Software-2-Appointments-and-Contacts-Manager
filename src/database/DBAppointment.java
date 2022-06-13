package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DBAppointment {

    /**
     * This gets a list of Appointments from the database
     * @return a observable list of appointments
     * @throws SQLException catches SQL Exceptions and returns a null list
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList(); // empty list of appointments to return

        String sql = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID;"; //sql
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        try {
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            while (rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppointment = new Appointment(id,title,description,location,type,startTime,endTime,customerID,userID,contactID,contactName);
                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This gets a list of Appointments from the database
     * @return a observable list of appointments
     * @throws SQLException catches SQL Exceptions and returns a null list
     */
    public static ObservableList<Appointment> getMonthAppointments() throws SQLException {

        //todo fix this make sure its doing the correct date addition
        ObservableList<Appointment> appointments = FXCollections.observableArrayList(); // empty list of appointments to return
        LocalDateTime todayDate = LocalDateTime.now();
        LocalDateTime nextMonth = todayDate.plusDays(30);

        String sql = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE a.Start > ? AND a.Start < ?"; //sql
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setDate(1,java.sql.Date.valueOf(todayDate.toLocalDate()));
        ps.setDate(2,java.sql.Date.valueOf(nextMonth.toLocalDate()));

        try {
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            while (rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppointment = new Appointment(id,title,description,location,type,startTime,endTime,customerID,userID,contactID,contactName);
                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<Appointment> getWeekAppointments() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList(); // empty list of appointments to return
        LocalDateTime todayDate = LocalDateTime.now();
        LocalDateTime nextMonth = todayDate.plusDays(7);

        String sql = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE a.Start > ? AND a.Start < ?"; //sql
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setDate(1,java.sql.Date.valueOf(todayDate.toLocalDate()));
        ps.setDate(2,java.sql.Date.valueOf(nextMonth.toLocalDate()));

        try {
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            while (rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppointment = new Appointment(id,title,description,location,type,startTime,endTime,customerID,userID,contactID,contactName);
                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns a list of Appointments that a customer is apart of
     * @param customerIDSearch int ID to search fro
     * @return list of appointments that the customer is apart of
     * @throws SQLException returns null if SQL Exception
     */
    public static ObservableList<Appointment> getAppointmentsByCustID(int customerIDSearch ) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList(); // empty list of appointments to return

        String sql = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Customer_ID=?;"; //sql
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1,customerIDSearch);

        try {
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            while (rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppointment = new Appointment(id,title,description,location,type,startTime,endTime,customerID,userID,contactID,contactName);
                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes all appointments with an associated Customer ID
     * @param customerIDSearch ID to search by
     * @return true if deletion worked
     * @throws SQLException catches and returns false
     */
    public static boolean deleteAppointmentsByCustID(int customerIDSearch) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Customer_ID=?"; //sql
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1,customerIDSearch);

        try {
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds provided appointment to the database
     * @param appointment Appointment object
     * @throws SQLException caches returns false if exception
     */
    public static boolean createAppointment(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1,appointment.getTitle());
        ps.setString(2,appointment.getDescription());
        ps.setString(3,appointment.getLocation());
        ps.setString(4,appointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf(appointment.getStartTime()));
        ps.setTimestamp(6,Timestamp.valueOf(appointment.getEndTime()));
        ps.setInt(7,appointment.getCustomerID());
        ps.setInt(8,appointment.getContactID());
        ps.setInt(9,appointment.getUserID());

        try {
            ps.execute();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

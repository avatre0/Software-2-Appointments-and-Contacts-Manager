package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUser {

    /**
     * Query's Database and generates list of all users as an Observable List
     * @return Observable List of users
     * @throws SQLException
     */
    public static ObservableList<User> queryUser() throws SQLException {
        ObservableList<User> userList = FXCollections.observableArrayList();// Creates a new list
        String sql = "SELECT * FROM USERS"; // Sql statement
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //turns that's statement into a prepared Statements
        ResultSet rs = ps.executeQuery(); // run the statement and stores the results in a ResultSet
        // While loop to loop through all results
        while (rs.next()){
            int id = rs.getInt("User_ID");
            String name = rs.getString("User_Name");
            String password = rs.getString("Password");

            // Creates new user with info from database
            User newUser = new User(id,name,password);
            userList.add(newUser); // adds that to the observable list
        }
        return userList;
    }

    /**
     * Verify's that the username is a valid username
     * @param userName provided username
     * @return bool if its a valid username
     * @throws SQLException
     */
    public static boolean verifyUserName(String userName) throws SQLException {
        String sql = "SELECT * FROM USERS"; // sql statement
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); // prepared Statement
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            if (rs.getString("User_Name").equals(userName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifyies proved username and password in the database
     * @param userName username of user
     * @param password provied password of that user
     * @return bool if username password combo is correct
     * @throws SQLException
     */
    public static boolean verifyPassword(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name=?"; // sql statement
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); // prepared Statement
        ps.setString(1,userName);
        ResultSet rs = ps.executeQuery();
        rs.next();
        if (rs.getString("Password").equals(password)){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Gets a list of all user objects
     * @return ObservableList of users
     * @throws SQLException catches
     */
    public static ObservableList<User> getUserList() throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User newUser = new User(id, userName, password);
                users.add(newUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}

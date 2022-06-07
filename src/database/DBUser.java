package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
}

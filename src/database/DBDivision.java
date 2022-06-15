package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Helper Class to work with divisions in the DB
 */
public class DBDivision {
    /*
     * Gets a list of Divisions
     * @return Observable List of Divisions
     *
    *
    public static ObservableList<Division> getDivisions()  {
        ObservableList<Division> divisions = FXCollections.observableArrayList(); // new list of divisions

        String sql = "SELECT * FROM first_level_divisions;"; //SQL Statement
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); // new prepared statement from sql

        //try to execute the query
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            //loop though results and builds a list of division objects
            while (rs.next()) {
                Division newDivision = new Division(rs.getInt("Division_ID"),rs.getString("Division"),rs.getInt("Country_ID"));
                divisions.add(newDivision);
            }
            return divisions;
            // catches SQL Exception
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
     */

    /**
     * Gets a division based on a proved name
     * @param divisionName Name of Division to find
     * @return division object with the name provided
     */
    public static Division getDivisionIDByName(String divisionName) {
        String sql = "SELECT * FROM first_level_divisions WHERE Division=?;"; //SQL Statement
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1,divisionName);

        //try to execute the query
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            //Builds a new division object
            while (rs.next()) {
                return new Division(rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("Country_ID"));
            }

            // catches SQL Exception
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * Gets a division based on a proved name
     * @param country Name of Divisions that you want
     * @return Observable List of divisions objects with of the country provided
     */
    public static ObservableList<Division> getDivisionsByCountry(Country country) {
        ObservableList<Division> divisions = FXCollections.observableArrayList(); // new list of divisions

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID=?;"; //SQL Statement
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); // new prepared statement from sql
            int id = country.getId();
            ps.setInt(1, id);

            //try to execute the query
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            //loop though results and builds a list of division objects
            while (rs.next()) {
                Division newDivision = new Division(rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("Country_ID"));
                divisions.add(newDivision);
            }
            return divisions;
            // catches SQL Exception
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}

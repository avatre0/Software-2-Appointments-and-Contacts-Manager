package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Helper class to handle working with Country's in the DB
 */
public class DBCountry {

    /**
     * Gets a list of Country Objects
     * @return ObservableList of country's
     */
    public static ObservableList<Country> getCountries() {
        ObservableList<Country> countries = FXCollections.observableArrayList(); // new list of countries

        String sql = "SELECT * FROM countries;"; //SQL Statement
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); // new prepared statement from sql

        //try to execute the query
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            //loop though results and builds a list of country objects
            while (rs.next()) {
                Country newCountry = new Country(rs.getInt("Country_ID"),rs.getString("Country"));
                countries.add(newCountry);
            }
            return countries;
        // catches SQL Exception
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves a country given its name
     * @param countryName String of a country value
     * @return Country object with input string value or null if no value exists
     */
    public static Country getCountryIDByName(String countryName) {

        String sql = "SELECT * FROM countries WHERE Country=?;"; //SQL Statement
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); // new prepared statement from sql
            ps.setString(1,countryName);

        //try to execute the query
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            //Builds a new country from the sql result
            while (rs.next()) {
                Country newCountry = new Country(rs.getInt("Country_ID"), rs.getString("Country"));
                return newCountry;
            }
        // catches SQL Exception
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        return null;
    }
}

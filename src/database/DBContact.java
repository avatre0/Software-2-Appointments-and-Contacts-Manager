package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContact {
    /**
     * Searches and returns a ID of a contact by provided name
     * @param contactName String of a name of a contact to search
     * @return int of the ID of the provided String
     * @throws SQLException Catches SQL Exception and returns 0 if none were found
     */
    public static int getContactID(String contactName) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_Name=?;"; // sql
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1,contactName);

        int id = 0;
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                id = rs.getInt("Contact_ID");
            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Gets all Contacts from database
     * @return Observable List of contacts
     * @throws SQLException catches
     */
    public static ObservableList<Contact> getContactList() throws SQLException {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact newContact = new Contact(id, name, email);
                contacts.add(newContact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }

    /**
     * Retrieves a contact by ID
     * @param id int contact ID to search for
     * @return a contact, if contact has a id of 0 no contact found.
     * @throws SQLException catches and prints error
     */
    public static Contact getContactByID(int id) throws SQLException {
        Contact newContact = new Contact(0,"","");
        String sql = "SELECT * FROM contacts WHERE Contact_ID=?;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1,id);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int contactID = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                newContact.setId(contactID);
                newContact.setName(name);
                newContact.setEmail(email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newContact;
    }
}

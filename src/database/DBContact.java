package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Helper class to interact with Contact's in the database
 */
public class DBContact {
    /**
     * Searches and returns a ID of a contact by provided name
     * @param contactName String of a name of a contact to search
     * @return int of the ID of the provided String, 0 if exception occurred
     */
    public static int getContactID(String contactName) {
        String sql = "SELECT * FROM contacts WHERE Contact_Name=?;"; // sql
        int id = 0;
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1,contactName);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                id = rs.getInt("Contact_ID");
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return id;
        }
    }

    /**
     * Gets all Contacts from database
     * @return Observable List of contacts, returns a empty list if exception
     */
    public static ObservableList<Contact> getContactList() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts;";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact newContact = new Contact(id, name, email);
                contacts.add(newContact);
            }
            return contacts;
        } catch (Exception e) {
            e.printStackTrace();
            return contacts;
        }
    }

    /**
     * Retrieves a contact by ID
     * @param id int contact ID to search for
     * @return a contact, if contact has a id of 0 no contact found.
     */
    public static Contact getContactByID(int id) {
        Contact newContact = new Contact(0,"","");
        String sql = "SELECT * FROM contacts WHERE Contact_ID=?;";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1,id);
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

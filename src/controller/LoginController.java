package controller;

import database.DBUser;
import database.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.LogName;
import util.Utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Login's FXML Controller
 * Has a lambda expression for file name for the log
 */
public class LoginController implements Initializable {

    private ResourceBundle resourceBundle;

    Stage stage;
    Parent scene;

    public Label titleText;
    public Label userNameText;
    public Label passwordText;
    public Label timeZoneText;
    public TextField userNameBox;
    public TextField passwordBox;
    public TextField timeZoneBox;
    public Button loginButton;
    public Button exitButton;

    /**
     * Lambda Expression #1
     * This efficiently and with minimal code sets the string for what the log will be called
     */
    LogName logName = () -> "login_activity.txt";

    /**
     * Creates a new File from a lambda expression value
     * If file exists it doest do anything.
     */
    private void createFile(){
        try {
            File newFile = new File(logName.getfileName());
            newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes to file a login failure with username and timestamp
     * retrieves filename from lambda expression
     */
    private void loginFail(){
        try {
            FileWriter fileWriter = new FileWriter(logName.getfileName(),true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("Login Failed: Username=" + userNameBox.getText() + " Timestamp: " + simpleDateFormat.format(date) +"\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates Login on login button press
     * @param actionEvent
     */
    @FXML
    private void login(ActionEvent actionEvent) {
        String userName = userNameBox.getText();
        String password = passwordBox.getText();
        resourceBundle = ResourceBundle.getBundle("language/language", Locale.getDefault());

        if (userName.isBlank()){
            Utilities.informationDisplay(resourceBundle.getString("errorDialog"),resourceBundle.getString("usernameRequired"));
        }
        if (password.isBlank()){
            Utilities.informationDisplay(resourceBundle.getString("errorDialog"),resourceBundle.getString("passwordRequired"));
        }
        else{
            if (DBUser.verifyUserName(userName) && DBUser.verifyPassword(userName,password)){
                loginSuccessful(actionEvent);
            }
            else{
                Utilities.errorDisplay(resourceBundle.getString("error"),resourceBundle.getString("incorrectUsernamePassword"));
                loginFail();
            }
        }
    }

    /**
     * Exit button action asks the user if they want to exit the program or not
     * Detects local language of en or fr
     * @param actionEvent button press
     */
    @FXML
    private void exit(ActionEvent actionEvent) {
        String title = null;
        String message = null;
        resourceBundle = ResourceBundle.getBundle("language/language", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            title = resourceBundle.getString("title");
            message = resourceBundle.getString("exitError");
        }

        if (Utilities.confirmDisplay(title, message)) {
            JDBC.closeConnection();
            System.exit(0);
        }
    }

    /**
     * Writes to file login successful with the username and timestamp
     * retreives filename from lambda expression
     * @param actionEvent button press
     */
    private void loginSuccessful(ActionEvent actionEvent) {
        try {
            FileWriter fileWriter = new FileWriter(logName.getfileName(),true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("Login Success: Username=" + userNameBox.getText() + " Timestamp: " + simpleDateFormat.format(date) +"\n");
            fileWriter.close();

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setTitle("Appointments and Customer Manager");
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the login view
     * Looks for default local, if french changes login page to french
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resourceBundle = ResourceBundle.getBundle("language/language", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            titleText.setText(resourceBundle.getString("title"));
            userNameText.setText(resourceBundle.getString("username"));
            passwordText.setText(resourceBundle.getString("password"));
            timeZoneText.setText(resourceBundle.getString("timezone"));
            loginButton.setText(resourceBundle.getString("login"));
            exitButton.setText(resourceBundle.getString("cancel"));
        }
        timeZoneBox.setText(ZoneId.systemDefault().getId());
        createFile();
    }
}

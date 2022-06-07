package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements Initializable {

    private ResourceBundle resourceBundle;

    public Label titleText;
    public Label userNameText;
    public Label passwordText;
    public Label timeZoneText;
    public TextField userNameBox;
    public TextField passwordBox;
    public TextField timeZoneBox;
    public Button loginButton;
    public Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resourceBundle = ResourceBundle.getBundle("Language/language", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            titleText.setText(resourceBundle.getString("title"));
            userNameText.setText(resourceBundle.getString("username"));
            passwordText.setText(resourceBundle.getString("password"));
            timeZoneText.setText(resourceBundle.getString("timezone"));
            timeZoneBox.setText(resourceBundle.getString("country"));
            loginButton.setText(resourceBundle.getString("login"));
            exitButton.setText(resourceBundle.getString("cancel"));
        }
    }

    public void login(ActionEvent actionEvent) {
    }

    public void exit(ActionEvent actionEvent) {
    }
}

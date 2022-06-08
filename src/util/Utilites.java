package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Utilites {

    /**
     * Alert Generator
     * @param title text for the title of the Alert
     * @param message text for the message of the Alert
     */
    public static boolean confirmDisplay(String title, String message) {

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.WARNING,"test",yes,no);
        alert.setTitle(title);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(no) == yes;
    }

    public static void informationDisplay(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void errorDisplay(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void utcToLocal() {
        //Todo
    }

    public static void utcToEastern() {
        //Todo
    }

    public static void systemToUtc() {
        //Todo
    }

}

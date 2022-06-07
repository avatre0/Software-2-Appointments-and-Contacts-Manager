package util;

import javafx.scene.control.Alert;

public class Utilites {
    /**
     * Alert Generator
     * @param title text for the title of the Alert
     * @param message text for the message of the Alert
     */
    public void alertDisplay(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void utcToLocal() {
        //Todo
    }

    public void utcToEastern() {
        //Todo
    }

    public void systemToUtc() {
        //Todo
    }

}

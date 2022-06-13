package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public class Utilities {

    /**
     * Alert Generator
     * @param title text for the title of the Alert
     * @param message text for the message of the Alert
     * @return bool if user selected yes or no
     */
    public static boolean confirmDisplay(String title, String message) {

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"test",yes,no);
        alert.setTitle(title);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(no) == yes;
    }

    /**
     * Alert Generator
     * @param title text for the title of the Alert
     * @param message text for the message of the Alert
     */
    public static void informationDisplay(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Alert Generator
     * @param title text for the title of the Alert
     * @param message text for the message of the Alert
     */
    public static void errorDisplay(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Converts UTC to local time
     * @param localDateTime UTC time
     * @return Local Time
     */
    public static LocalDateTime utcToLocal(LocalDateTime localDateTime) {
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime localzdt = zdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        return localzdt.toLocalDateTime();
    }

    /**
     * Converts Local time to UTC
     * @param localDateTime Local Time
     * @return UTC Time
     */
    public static LocalDateTime localToUTC(LocalDateTime localDateTime) {
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utczdt = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        return utczdt.toLocalDateTime();
    }

    /**
     * Converts Local to EST
     * @param localDateTime Local Time
     * @return EST Time
     */
    public static LocalDateTime localToEST(LocalDateTime localDateTime) {
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime estzdt = zdt.withZoneSameInstant(ZoneId.of("America/New_York"));
        return estzdt.toLocalDateTime();
    }

    /**
     * Converts EST to Local
     * @param localDateTime EST Time
     * @return Local Time
     */
    public static LocalDateTime estToLocal(LocalDateTime localDateTime) {
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime localzdt = zdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        return localzdt.toLocalDateTime();
    }
}

package utility;

import javafx.scene.control.Alert;

public class DatabaseException extends Exception {
    private String error;

    public DatabaseException(String s) {
        super(s);
        error = s;
    }

    public void show() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(error);
        alert.showAndWait();
    }
}

package duke.ui;

import javafx.scene.control.Alert;
import javafx.stage.Window;

//@@author wjlingg
/**
 * Controller to show prompt for help.
 */
public class AlertHelper {

    /**
     * Shows prompt message.
     */
    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}

package JavaFx;

import javafx.scene.control.*;
import java.util.Optional;

/**
 * Controller for user prompt. Provides the layout for different alert types.
 */
public class AlertBox {
    /**
     * This function generates a alert box based on alert type
     * @param title The title of alert
     * @param header The header of the alert
     * @param message The body of the alert
     * @param alertType The alert type
     * @return This returns the option chosen by the user input
     */
    public static boolean display(String title, String header, String message, Alert.AlertType alertType){
        boolean isOk = true;
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        if(alertType == Alert.AlertType.CONFIRMATION) {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                isOk = true;
            } else if (result.get() == ButtonType.CANCEL){
                isOk = false;
            }
        } else if (alertType == Alert.AlertType.INFORMATION || alertType == Alert.AlertType.WARNING){
            alert.showAndWait();

        }
        alert.close();

        return isOk;
    }
}

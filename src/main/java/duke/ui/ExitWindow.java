package duke.ui;

import duke.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ExitWindow extends HBox {

    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    @FXML
    private Label exitMessage;

    private static final String EXIT_MESSAGE = "Are you sure to exit?";

    public ExitWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/ExitWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        exitMessage.setText(EXIT_MESSAGE);
    }

    @FXML
    private void confirmExit() {
        System.exit(0);
    }

    @FXML
    private void notConfirmExit() {
        Stage stage = (Stage) noButton.getScene().getWindow();
        stage.close();
    }
}

package optix.ui.windows;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainWindow extends AnchorPane {

    @FXML
    ScrollPane scrollPane;

    @FXML
    private VBox display;

    @FXML
    private Label optixResponse;

    @FXML
    private JFXTextField userInput;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(display.heightProperty());
    }

    @FXML
    private void handleUserInput() {
        String inputText = userInput.getText();
        optixResponse.setText(inputText);
//        display.getChildren().add(ListView.handleUserInput(userInput.getText()));
        userInput.clear();
    }
}

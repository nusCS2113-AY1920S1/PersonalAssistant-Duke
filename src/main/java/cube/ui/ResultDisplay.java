package cube.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class ResultDisplay extends UiManager<StackPane> {
    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    public void setResultText(String input) {
        resultDisplay.setText(input);
    }
}

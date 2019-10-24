package cube.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.control.TextArea;
import javafx.fxml.FXML;

public class ResultDisplay extends UiManager<StackPane>  {
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

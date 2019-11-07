package cube.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatusBar extends UiManager<VBox> {
    private static final String FXML = "StatusBar.fxml";

    @FXML
    private Label saveLocationStatus;

    public StatusBar(String fileFullPath) {
        super(FXML);
        this.saveLocationStatus.setText(fileFullPath);
    }
}

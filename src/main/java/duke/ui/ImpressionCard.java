package duke.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class ImpressionCard extends UiElement<Region> {
    private static final String FXML = "ImpressionCard.fxml";

    @FXML
    private Label nameLabel;
    @FXML
    private Label criticalLabel;
    @FXML
    private Label followupLabel;
    @FXML
    private Label descriptionLabel;

    public ImpressionCard() {
        super(FXML, null);
    }
}

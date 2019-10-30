package duke.ui.card;

import duke.data.Help;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelpCard extends UiCard {
    private static final String FXML = "HelpCard.fxml";

    @FXML
    private Label commandLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label formatLabel;
    @FXML
    private Label switchLabel;

    public HelpCard(Help help) {
        super(FXML);

        commandLabel.setText(help.getCommand());
        descriptionLabel.setText(help.getDescription());
        formatLabel.setText(help.getFormat());
        switchLabel.setText(help.getSwitches());
    }
}

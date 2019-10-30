package duke.ui.card;

import duke.data.Help;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * A UI card that displays all of the information of a {@code Help}.
 */
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
    @FXML
    private VBox mainSection;
    @FXML
    private VBox detailsSection;

    /**
     * Constructs a HelpCard object with the specified {@code Help}'s details.
     *
     * @param help           Help object.
     * @param isDetailsShown If the details for the Help object should be shown.
     */
    public HelpCard(Help help, boolean isDetailsShown) {
        super(FXML);

        commandLabel.setText(help.getCommand());
        descriptionLabel.setText(help.getSummary());
        formatLabel.setText(help.getFormat());
        switchLabel.setText(help.getSwitches());

        if (!isDetailsShown) {
            mainSection.getChildren().remove(detailsSection);
        }
    }
}

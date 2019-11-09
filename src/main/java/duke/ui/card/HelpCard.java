package duke.ui.card;

import duke.data.Help;
import duke.exception.DukeFatalException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HelpCard extends UiCard {
    private static final String FXML = "HelpCard.fxml";

    @FXML
    private Label commandLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private VBox formatSection;
    @FXML
    private Label formatLabel;
    @FXML
    private VBox switchSection;
    @FXML
    private Label switchLabel;
    @FXML
    private VBox exampleSection;
    @FXML
    private Label exampleLabel;
    @FXML
    private VBox infoSection;
    @FXML
    private Label infoLabel;
    @FXML
    private VBox mainSection;
    @FXML
    private VBox detailsSection;
    private Help help;

    /**
     * Constructs a HelpCard object with the specified {@code Help}'s details.
     *
     * @param help           Help object.
     * @param isDetailsShown If the details for the Help object should be shown.
     */
    public HelpCard(final Help help, final boolean isDetailsShown) throws DukeFatalException {
        super(FXML);

        this.help = help;
        this.commandLabel.setText(help.getCommand());
        this.descriptionLabel.setText(help.getSummary());
        this.formatLabel.setText(help.getFormat());

        if (!isDetailsShown) {
            this.mainSection.getChildren().remove(this.detailsSection);
            return;
        }

        if (help.getSwitches() == null) {
            this.detailsSection.getChildren().remove(this.switchSection);
        } else {
            this.switchLabel.setText(help.getSwitches());
        }

        if (help.getInfo() == null) {
            this.detailsSection.getChildren().remove(this.infoSection);
        } else {
            this.infoLabel.setText(help.getInfo());
        }

        if (help.getExample() == null) {
            this.detailsSection.getChildren().remove(this.exampleSection);
        } else {
            this.exampleLabel.setText(help.getExample());
        }
    }

    public Help getHelp() {
        return this.help;
    }
}

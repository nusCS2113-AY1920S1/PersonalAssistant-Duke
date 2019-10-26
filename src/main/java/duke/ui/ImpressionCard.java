package duke.ui;

import duke.DukeCore;
import duke.data.Impression;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * An UI element that displays basic information of a {@code Impression}.
 * TODO: Extend from UiElement.
 */
public class ImpressionCard extends AnchorPane {
    private static final String FXML = "ImpressionCard.fxml";

    @FXML
    private Label nameLabel;
    @FXML
    private Label criticalLabel;
    @FXML
    private Label followupLabel;
    @FXML
    private Label descriptionLabel;

    private Impression impression;

    /**
     * Constructs an ImpressionCard object with the specified impression's details.
     *
     * @param impression Impression object.
     * @param isPrimary  If the Impression object is a primary diagnosis.
     */
    ImpressionCard(Impression impression, boolean isPrimary) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DukeCore.class.getResource("/view/" + FXML));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }

        this.impression = impression;

        nameLabel.setText(impression.getName());
        criticalLabel.setText(impression.getTreatments().size() + " critical(s)");
        // TODO: followupLabel
        followupLabel.setText("0 follow-up(s)");
        descriptionLabel.setText(impression.getDescription());

        if (isPrimary) {
            setStyle("-fx-border-color:red; -fx-border-width: 3; -fx-border-style: solid;");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ImpressionCard)) {
            return false;
        }

        ImpressionCard card = (ImpressionCard) obj;
        return impression.equals(card.getImpression());
    }

    public Impression getImpression() {
        return impression;
    }
}

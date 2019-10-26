package duke.ui;

import duke.DukeCore;
import duke.data.Observation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class ObservationCard extends EvidenceCard {
    private static final String FXML = "ObservationCard.fxml";

    @FXML
    private Label nameLabel;
    @FXML
    private Label criticalLabel;
    @FXML
    private Label objectiveLabel;

    private Observation observation;

    /**
     * Constructs an ObservationCard object with the specified observation's details.
     *
     * @param observation Observation object.
     */
    ObservationCard(Observation observation) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DukeCore.class.getResource("/view/" + FXML));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }

        this.observation = observation;

        nameLabel.setText(observation.getName());
        objectiveLabel.setText(observation.isObjective() ? "Objective" : "Subjective");

        final String[] priorities = {"No Priority set", "Critical", "Urgent", "Compulsory", "Optional"};
        String priorityText = String.valueOf(observation.getPriority());
        if (observation.getPriority() >= 0 && observation.getPriority() < priorities.length) {
            priorityText += " - " + priorities[observation.getPriority()];
        }

        criticalLabel.setText(priorityText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ObservationCard)) {
            return false;
        }

        ObservationCard card = (ObservationCard) obj;
        return observation.equals(card.getObservation());
    }

    public Observation getObservation() {
        return observation;
    }
}

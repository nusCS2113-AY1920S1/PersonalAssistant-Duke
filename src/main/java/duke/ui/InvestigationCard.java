package duke.ui;

import duke.DukeCore;
import duke.data.Investigation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class InvestigationCard extends TreatmentCard {
    private static final String FXML = "InvestigationCard.fxml";

    @FXML
    private Label nameLabel;
    @FXML
    private Label criticalLabel;
    @FXML
    private Label statusLabel;

    private Investigation investigation;

    /**
     * Constructs an InvestigationCard object with the specified investigation's details.
     *
     * @param investigation Investigation object.
     */
    InvestigationCard(Investigation investigation) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DukeCore.class.getResource("/view/" + FXML));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }

        this.investigation = investigation;
        nameLabel.setText(investigation.getName());

        final String[] priorities = {"No Priority set", "Critical", "Urgent", "Compulsory", "Optional"};
        String priorityText = String.valueOf(investigation.getPriority());
        if (investigation.getPriority() >= 0 && investigation.getPriority() < priorities.length) {
            priorityText += " - " + priorities[investigation.getPriority()];
        }

        criticalLabel.setText(priorityText);

        String statusText = String.valueOf(investigation.getStatusIdx());
        if (investigation.getStatusArr() != null && investigation.getStatusIdx() >= 0
            && investigation.getStatusIdx() < investigation.getStatusArr().length) {
            statusText += " - " + investigation.getStatusArr()[investigation.getStatusIdx()];
        }
        statusLabel.setText(statusText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof InvestigationCard)) {
            return false;
        }

        InvestigationCard card = (InvestigationCard) obj;
        return investigation.equals(card.getInvestigation());
    }

    public Investigation getInvestigation() {
        return investigation;
    }
}

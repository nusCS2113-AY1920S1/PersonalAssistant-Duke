package duke.ui;

import duke.DukeCore;
import duke.data.Plan;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class PlanCard extends TreatmentCard {
    private static final String FXML = "PlanCard.fxml";

    @FXML
    private Label nameLabel;
    @FXML
    private Label criticalLabel;
    @FXML
    private Label statusLabel;

    private Plan plan;

    /**
     * Constructs an PlanCard object with the specified plan's details.
     *
     * @param plan Plan object.
     */
    PlanCard(Plan plan) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DukeCore.class.getResource("/view/" + FXML));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }

        this.plan = plan;
        nameLabel.setText(plan.getName());

        final String[] priorities = {"No Priority set", "Critical", "Urgent", "Compulsory", "Optional"};
        String priorityText = String.valueOf(plan.getPriority());
        if (plan.getPriority() >= 0 && plan.getPriority() < priorities.length) {
            priorityText += " - " + priorities[plan.getPriority()];
        }

        criticalLabel.setText(priorityText);

        String statusText = String.valueOf(plan.getStatusIdx());
        if (plan.getStatusArr() != null && plan.getStatusIdx() >= 0
            && plan.getStatusIdx() < plan.getStatusArr().length) {
            statusText += " - " + plan.getStatusArr()[plan.getStatusIdx()];
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

        if (!(obj instanceof PlanCard)) {
            return false;
        }

        PlanCard card = (PlanCard) obj;
        return plan.equals(card.getPlan());
    }

    public Plan getPlan() {
        return plan;
    }
}

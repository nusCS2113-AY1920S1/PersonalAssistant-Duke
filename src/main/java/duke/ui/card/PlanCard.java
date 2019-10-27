package duke.ui.card;

import duke.data.Plan;
import duke.data.Treatment;

public class PlanCard extends TreatmentCard {
    private static final String FXML = "PlanCard.fxml";

    private final Plan plan;

    /**
     * Constructs a PlanCard object with the specified {@code Plan}'s details.
     *
     * @param plan  Plan object.
     * @param index Displayed index.
     */
    public PlanCard(Plan plan, int index) {
        super(FXML, plan, index);

        this.plan = plan;
        fillPlanCard();
    }

    private void fillPlanCard() {
        nameLabel.setText(plan.getName());

        String statusText = String.valueOf(plan.getStatusIdx());
        if (plan.getStatusIdx() >= 0 && plan.getStatusIdx() < Plan.getStatusArr().size()) {
            statusText += " - " + plan.getStatusStr();
        }
        statusLabel.setText(statusText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            return true;
        }

        if (!(object instanceof PlanCard)) {
            return false;
        }

        PlanCard card = (PlanCard) object;
        return plan.equals(card.getTreatment());
    }

    @Override
    public Treatment getTreatment() {
        return plan;
    }
}

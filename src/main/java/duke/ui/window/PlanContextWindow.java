package duke.ui.window;

import duke.data.Plan;
import duke.exception.DukeFatalException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * UI window for the Plan context.
 */
public class PlanContextWindow extends DukeDataContextWindow {
    private static final String FXML = "PlanContextWindow.fxml";

    @FXML
    private Label statusLabel;
    @FXML
    private Label summaryLabel;

    private Plan plan;

    /**
     * Constructs a UI context window for a Plan object.
     *
     * @param plan Plan object.
     */
    public PlanContextWindow(Plan plan) throws DukeFatalException {
        super(FXML, plan);

        this.plan = plan;
        updateUi();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUi() {
        super.updateUi();
        statusLabel.setText(plan.getStatusStr());
        summaryLabel.setText(plan.getSummary());
    }
}
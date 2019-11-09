package duke.ui.window;

import duke.data.Investigation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * UI window for the Investigation context.
 */
public class InvestigationContextWindow extends DukeDataContextWindow {
    private static final String FXML = "InvestigationContextWindow.fxml";

    @FXML
    private Label statusLabel;

    private Investigation investigation;

    /**
     * Constructs a UI context window for an Investigation object.
     *
     * @param investigation Investigation object.
     */
    public InvestigationContextWindow(Investigation investigation) {
        super(FXML, investigation);

        this.investigation = investigation;

        updateUi();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUi() {
        super.updateUi();

        statusLabel.setText(investigation.getStatusStr());
    }
}

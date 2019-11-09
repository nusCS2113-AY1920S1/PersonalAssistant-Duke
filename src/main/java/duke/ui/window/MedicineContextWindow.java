package duke.ui.window;

import duke.data.Medicine;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * UI window for the Investigation context.
 */
public class MedicineContextWindow extends DukeDataContextWindow {
    private static final String FXML = "InvestigationContextWindow.fxml";

    @FXML
    private Label statusLabel;
    @FXML
    private Label doseLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label durationLabel;

    private Medicine medicine;

    /**
     * Constructs a UI context window for a Plan object.
     *
     * @param fxmlFileName Name of FXML file.
     * @param medicine     Medicine object.
     */
    public MedicineContextWindow(String fxmlFileName, Medicine medicine) {
        super(fxmlFileName, medicine);

        this.medicine = medicine;

        updateUi();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUi() {
        super.updateUi();

        statusLabel.setText(medicine.getStatusStr());
        doseLabel.setText(medicine.getDose());
        startDateLabel.setText(medicine.getStartDate());
        durationLabel.setText(medicine.getDuration());
    }
}

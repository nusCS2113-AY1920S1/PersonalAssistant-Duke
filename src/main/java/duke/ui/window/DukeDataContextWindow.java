package duke.ui.window;

import duke.data.DukeData;
import duke.data.DukeObject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

/**
 * Generic UI window for the DukeData (Treatment / Evidence) context.
 */
public class DukeDataContextWindow extends ContextWindow {
    @FXML
    private Label nameLabel;
    @FXML
    private Label summaryLabel;
    @FXML
    private Label priorityLabel;

    private DukeData data;

    /**
     * Constructs a generic UI context window for DukeData.
     *
     * @param fxmlFileName Name of FXML file.
     * @param data         DukeData object.
     */
    public DukeDataContextWindow(String fxmlFileName, DukeData data) {
        super(fxmlFileName);

        if (data == null) {
            return;
        }

        this.data = data;
        updateUi();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUi() {
        nameLabel.setText(data.getName());
        summaryLabel.setText(data.getSummary());
        priorityLabel.setText(String.valueOf(data.getPriority()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DukeObject> getIndexedList(String type) {
        return null;
    }

    public DukeData getData() {
        return data;
    }
}

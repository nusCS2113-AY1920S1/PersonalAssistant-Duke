package duke.ui.window;

import duke.data.DukeData;
import duke.exception.DukeFatalException;
import duke.ui.commons.UiStrings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Generic UI window for the DukeData (Treatment / Evidence) context.
 */
public class DukeDataContextWindow extends ContextWindow {
    @FXML
    private Label nameLabel;
    @FXML
    private Label priorityLabel;

    private DukeData data;

    /**
     * Constructs a generic UI context window for DukeData.
     *
     * @param fxmlFileName Name of FXML file.
     * @param data         DukeData object.
     * @throws DukeFatalException If {@code data} is null.
     */
    public DukeDataContextWindow(String fxmlFileName, DukeData data) throws DukeFatalException {
        super(fxmlFileName);

        if (data == null) {
            throw new DukeFatalException(UiStrings.MESSAGE_ERROR_UNINITIALISED_DUKEDATA);
        }

        this.data = data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUi() {
        nameLabel.setText(data.getName());
        priorityLabel.setText(String.valueOf(data.getPriority()));
    }

    public DukeData getData() {
        return data;
    }
}

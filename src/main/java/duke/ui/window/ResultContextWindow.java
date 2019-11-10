package duke.ui.window;

import duke.data.Result;
import duke.exception.DukeFatalException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * UI window for the Result context.
 */
public class ResultContextWindow extends DukeDataContextWindow {
    private static final String FXML = "ResultContextWindow.fxml";

    @FXML
    private Label summaryLabel;

    public ResultContextWindow(Result result) throws DukeFatalException {
        super(FXML, result);
        super.updateUi();
        summaryLabel.setText(result.getSummary());
    }
}

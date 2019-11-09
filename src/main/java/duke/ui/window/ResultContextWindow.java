package duke.ui.window;

import duke.data.Result;

/**
 * UI window for the Result context.
 */
public class ResultContextWindow extends DukeDataContextWindow {
    private static final String FXML = "ResultContextWindow.fxml";

    public ResultContextWindow(Result result) {
        super(FXML, result);
    }
}

package duke.ui;

import duke.DukeCore;
import duke.data.Result;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class ResultCard extends EvidenceCard {
    private static final String FXML = "ResultCard.fxml";

    @FXML
    private Label nameLabel;
    @FXML
    private Label criticalLabel;

    private Result result;

    /**
     * Constructs an ResultCard object with the specified result's details.
     *
     * @param result result object.
     */
    ResultCard(Result result) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DukeCore.class.getResource("/view/" + FXML));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }

        this.result = result;

        nameLabel.setText(result.getName());

        final String[] priorities = {"No Priority set", "Critical", "Urgent", "Compulsory", "Optional"};
        String priorityText = String.valueOf(result.getPriority());
        if (result.getPriority() >= 0 && result.getPriority() < priorities.length) {
            priorityText += " - " + priorities[result.getPriority()];
        }

        criticalLabel.setText(priorityText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ResultCard)) {
            return false;
        }

        ResultCard card = (ResultCard) obj;
        return result.equals(card.getResult());
    }

    public Result getResult() {
        return result;
    }
}

package duke.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

//@@author talesrune
/**
 * Controller for BudgetWindow. Provides the layout for the other controls.
 */
public class CreditsWindow extends AnchorPane {
    @FXML
    private TextArea taCreditsList;

    /**
     * Setting up Credits Window Interface.
     *
     * @param creditsDesc The credits details.
     */
    @FXML
    public void setCreditsWindow(String creditsDesc) {
        if (creditsDesc.trim().isEmpty()) {
            creditsDesc = "Unable to load credits.";
        }
        taCreditsList.setText(creditsDesc);
    }
}
//@@author
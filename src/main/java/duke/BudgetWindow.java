package duke;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

//@@author talesrune
/**
 * Controller for BudgetWindow. Provides the layout for the other controls.
 */
public class BudgetWindow extends AnchorPane {
    @FXML
    private TextArea taBudgetList;

    /**
     * Setting up Add Notes Window Interface.
     *
     *
     * @param budgetDesc The existing notes of the task.
     */
    @FXML
    public void setBudgetWindow(String budgetDesc) {
        taBudgetList.setText(budgetDesc);
    }
}
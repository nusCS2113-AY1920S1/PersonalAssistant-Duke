package duke.ui;

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
     * Setting up Add budget Window Interface.
     *
     * @param budgetDesc The existing notes of the task.
     * @param currBudget The current budget.
     */
    @FXML
    public void setBudgetWindow(String budgetDesc, float currBudget) {
        budgetDesc = "     Your current budget is : $" + currBudget + "\n" + budgetDesc;
        taBudgetList.setText(budgetDesc);
    }
}
//@@author
package duke;

import duke.dukeobject.Expense;
import duke.dukeobject.ExpenseList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.math.BigDecimal;

/**
 * Controller for MainWindow.fxml
 */
public class MainWindow extends BorderPane {
    @FXML
    public Label totalSpentLabel;
    @FXML
    public ListView<String> expenseListView;
    @FXML
    public BorderPane main;
    @FXML
    public TextField inputField;
    @FXML
    public Label lastCommandLabel;

    private Duke duke;

    /**
     * Detects enter key and passes command entered in the TextField into Duke, and update the GUI accordingly.
     */
    @FXML
    public void onEnter() {
        String userInput = inputField.getText();
        String response = duke.getResponse(userInput);
        lastCommandLabel.setText(response);
        updateExpenseListView();
        inputField.clear();
        updateTotalSpentLabel();
    }

    /**
     * Sets the duke object in MainWindow.
     *
     * @param d <code>Duke</code>Duke object
     */
    public void setDuke(Duke d) {
        this.duke = d;
        updateExpenseListView();
        updateTotalSpentLabel();
    }

    /**
     * Populate the ListView with a list of expenses.
     */
    private void updateExpenseListView() {
        expenseListView.getItems().clear();
        int count = 1;
        for (Expense expense : duke.expenseList.getExternalList()) {
            expenseListView.getItems().add(count + ". " + expense.toString());
            count++;
        }
    }

    /**
     * Updates the total amount label.
     */
    public void updateTotalSpentLabel() {
        totalSpentLabel.setText("Total: " + ((duke.expenseList.getTotalAmount().compareTo(BigDecimal.valueOf(0)) < 0) ? "-$" + duke.expenseList.getTotalAmount().abs() : "$" + duke.expenseList.getTotalAmount()));
    }
}


import dukeobjects.Expense;
import dukeobjects.ExpenseList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

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
        ExpenseList expenseList = (ExpenseList) duke.getExpenseList();
        setExpenseListView(expenseList);
        inputField.clear();
        updateTotalSpentLabel(duke.getExpenseList());
    }

    /**
     * Sets the duke object in MainWindow.
     *
     * @param d <code>Duke</code>Duke object
     */
    public void setDuke(Duke d) {
        this.duke = d;
        updateTotalSpentLabel(d.getExpenseList());
    }

    /**
     * Populate the ListView with a list of expenses.
     */
    private void setExpenseListView(ExpenseList expensesList) {
        expenseListView.getItems().clear();
        int count = 1;
        for (Expense expense : expensesList.getExpenseList()) {
            expenseListView.getItems().add(count + ". " + expense.toString());
            count++;
        }
    }

    /**
     * Updates the total amount label.
     *
     * @param expenseList The <code>ExpenseList</code> object stored in Duke
     */
    public void updateTotalSpentLabel(ExpenseList expenseList) {
        totalSpentLabel.setText("Total: $" + expenseList.getTotalAmount());
    }
}


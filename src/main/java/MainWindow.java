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
    private Duke duke;
    @FXML
    public ListView<String> expenseListView;
    @FXML
    public BorderPane main;
    @FXML
    public TextField inputField;
    @FXML
    public Label lastCommandLabel;

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
    }

    /**
     * Sets the duke object in MainWindow.
     * @param d <code>Duke</code>Duke object
     */
    public void setDuke(Duke d) {
        this.duke = d;
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
}


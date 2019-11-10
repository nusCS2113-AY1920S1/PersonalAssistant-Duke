package duke.ui;

import duke.model.Expense;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class ExpenseCard extends UiPart<Region> {
    private static final String FXML_FILE_NAME = "ExpenseCard.fxml";
    public final Expense expense;

    @FXML
    private Label description;
    @FXML
    private Label amount;
    @FXML
    private Label tag;
    @FXML
    private Label date;
    @FXML
    private VBox expenseContainer;

    /**
     * Constructor of controller for ExpenseCard.fxml.
     * @param expense The Expense object we wish to display
     * @param index the int index of the current Expense in list of Expenses we are displaying
     */
    public ExpenseCard(Expense expense, int index) {
        super(FXML_FILE_NAME, null);
        this.expense = expense;
        description.setText(index + ". " + expense.getDescription());
        amount.setText("$" + expense.getAmount().toString());
        tag.setText("Tag: " + expense.getTag());
        date.setText(expense.getTimeString());
        if (expense.isRecurring()) {
            description.setTextFill(Color.GREEN);
            amount.setTextFill(Color.GREEN);
            tag.setTextFill(Color.GREEN);
            date.setTextFill(Color.GREEN);
        } else if (expense.isTentative()) {
            description.setTextFill(Color.GRAY);
            amount.setTextFill(Color.GRAY);
            tag.setTextFill(Color.GRAY);
            date.setTextFill(Color.GRAY);
        }
    }
}

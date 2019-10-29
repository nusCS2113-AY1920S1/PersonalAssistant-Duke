package duke.ui;

import duke.model.Expense;
import duke.model.PlanBot;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class ExpenseCard extends UiPart<Region>{
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


    public ExpenseCard(Expense expense) {
        super(FXML_FILE_NAME, null);
        this.expense = expense;
        description.setText(expense.getDescription());
        amount.setText("$" + expense.getAmount().toString());
        tag.setText("Tag: " + expense.getTag());
        date.setText(expense.getTimeString());
        if (expense.isRecurring()) {
            description.setTextFill(Color.GREEN);
            amount.setTextFill(Color.GREEN);
            tag.setTextFill(Color.GREEN);
            date.setTextFill(Color.GREEN);
        } else if(expense.isTentative()) {
            description.setTextFill(Color.GRAY);
            amount.setTextFill(Color.GRAY);
            tag.setTextFill(Color.GRAY);
            date.setTextFill(Color.GRAY);
        }
    }
}

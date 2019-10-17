package duke;

import duke.dukeobject.Expense;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.math.BigDecimal;

/**
 * Controller for MainWindow.fxml
 */
public class MainWindow extends BorderPane {

    @FXML
    public Label totalSpentLabel;
    @FXML
    public BorderPane main;
    @FXML
    public TextField inputField;
    @FXML
    public Label lastCommandLabel;
    @FXML
    public Label monthlyBudgetLabel;
    @FXML
    Label remainingBudgetLabel;
    @FXML
    TableView expenseTableView;
    @FXML
    ListView budgetListView = new ListView();

    private Duke duke;

    /**
     * Detects enter key and passes command entered in the TextField into Duke, and update the GUI accordingly.
     */
    @FXML
    public void onEnter() {
        String userInput = inputField.getText();
        String response = duke.getResponse(userInput);
        lastCommandLabel.setText(response);
        inputField.clear();
        updateTotalSpentLabel();
        updateTableListView();
        updateMonthlyBudget();
        updateRemainingBudget();
        updateBudgetListView();
    }

    /**
     * Sets the duke object in MainWindow.
     *
     * @param d <code>Duke</code>Duke object
     */
    public void setDuke(Duke d) {
        this.duke = d;
        updateTotalSpentLabel();
        updateMonthlyBudget();
        updateRemainingBudget();
        updateTableListView();
        updateBudgetListView();

    }

    /**
     * Populate the ListView with a list of expenses.
     */

    private void updateTableListView() {
        expenseTableView.getItems().clear();
        expenseTableView.setPlaceholder(new Label("No expenses to display!"));
        TableColumn<Expense, Void> indexColumn = new TableColumn<>("No.");
        indexColumn.setCellFactory(col -> {
            TableCell<Expense, Void> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                if (cell.isEmpty()) {
                    return null;
                } else {
                    return Integer.toString(cell.getIndex() + 1);
                }
            }, cell.emptyProperty(), cell.indexProperty()));
            return cell;
        });

        TableColumn<String, Expense> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeString"));
        TableColumn<String, Expense> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        TableColumn<Expense, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<Expense, String> tagColumn = new TableColumn<>("Tags");
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tagsString"));
        TableColumn<Expense, Boolean> isTentativeColumn = new TableColumn<>("Tentative");
        isTentativeColumn.setCellValueFactory(new PropertyValueFactory<>("tentative"));
        expenseTableView.getColumns().setAll(
            indexColumn,
            timeColumn,
            amountColumn,
            descriptionColumn,
            tagColumn,
            isTentativeColumn);
        for (Expense expense : duke.expenseList.getExternalList()) {
            expenseTableView.getItems().add(expense);
        }
    }


    /**
     * Updates the total amount label.
     */
    public void updateTotalSpentLabel() {
        totalSpentLabel.setText("Total: "
            + ((duke.expenseList.getTotalAmount().compareTo(BigDecimal.valueOf(0)) < 0)
            ? "-$" + duke.expenseList.getTotalAmount().abs() : "$"
            + duke.expenseList.getTotalAmount()));
    }

    /**
     * Updates the monthly budget label.
     */
    public void updateMonthlyBudget() {
        monthlyBudgetLabel.setText("Budget: " + duke.budget.getMonthlyBudgetString());
    }

    /**
     * Updates the remaining budget label.
     */
    public void updateRemainingBudget() {
        remainingBudgetLabel.setText("Remaining: "
            + ((duke.budget.getRemaining(duke.expenseList.getTotalAmount()).compareTo(BigDecimal.valueOf(0)) < 0)
            ? "-$" + duke.budget.getRemaining(duke.expenseList.getTotalAmount()).abs()
            : "$" + duke.budget.getRemaining(duke.expenseList.getTotalAmount())));
    }

    /**
     * Updates the Budget List of all categories.
     */
    public void updateBudgetListView() {
        budgetListView.getItems().clear();
        budgetListView.getItems().add("Tag: Spent/Budget");
        for (String tag : duke.budget.getBudgetCategory().keySet()) {
            budgetListView.getItems().add(tag
                + ": $" + duke.expenseList.getTagAmount(tag)
                + "/$" + duke.budget.getBudgetCategory().get(tag));
        }
    }

}


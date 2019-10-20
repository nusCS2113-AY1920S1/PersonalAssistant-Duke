package duke;

import duke.dukeobject.Expense;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

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
    ListView<String> budgetListView = new ListView<>();

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
        indexColumn.setSortable(false);
        TableColumn<String, Expense> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeString"));
        timeColumn.setSortable(false);
        TableColumn<String, Expense> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountColumn.setSortable(false);
        TableColumn<Expense, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setSortable(false);
        TableColumn<Expense, String> tagColumn = new TableColumn<>("Tags");
        tagColumn.setSortable(false);
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tagsString"));
        tagColumn.setSortable(false);

        expenseTableView.setRowFactory(new Callback<TableView<Expense>, TableRow<Expense>>() {
            @Override
            public TableRow<Expense> call(TableView<Expense> tableView) {
                final TableRow<Expense> row = new TableRow<Expense>() {
                    @Override
                    protected void updateItem(Expense expense, boolean empty) {
                        super.updateItem(expense, empty);
                        if (expense != null && expense.isTentative()) {
                            setStyle("-fx-text-background-color: blue;");

                        } else {
                            setStyle("-fx-text-background-color: black;");
                        }
                    }
                };
                return row;
            }
        });
        expenseTableView.getColumns().setAll(
                indexColumn,
                timeColumn,
                amountColumn,
                descriptionColumn,
                tagColumn
        );

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


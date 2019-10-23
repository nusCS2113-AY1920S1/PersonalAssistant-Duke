package duke.ui;

import duke.commons.LogsCenter;
import duke.model.Expense;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.util.logging.Logger;

public class ExpensePane extends UiPart<BorderPane> {

    private static final Logger logger = LogsCenter.getLogger(ExpensePane.class);

    private static final String FXML_FILE_NAME = "ExpensePane.fxml";

    @FXML
    TableView expenseTableView;

    public ExpensePane(ObservableList<Expense> expenseList) {
        super(FXML_FILE_NAME, null);
        logger.info("expenseList has length " + expenseList.size());
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
                            setStyle("-fx-text-background-color: grey;");

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
        logger.info("Items are set.");
        for (Expense expense : expenseList) {
            expenseTableView.getItems().add(expense);
        }
        logger.info("cell factory is set.");
    }

}
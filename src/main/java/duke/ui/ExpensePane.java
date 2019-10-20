package duke.ui;

import duke.commons.LogsCenter;
import duke.model.Expense;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ListCell;

import java.util.logging.Logger;

public class ExpensePane extends UiPart<AnchorPane> {

    private static final Logger logger = LogsCenter.getLogger(ExpensePane.class);

    private static final String FXML_FILE_NAME = "ExpensePane.fxml";

    @FXML
    private ListView<Expense> expenseListView;

    public ExpensePane(ObservableList<Expense> expenseList) {
        super(FXML_FILE_NAME, null);
        logger.info("expenseList has length " + expenseList.size());
        expenseListView.setItems(expenseList);
        logger.info("Items are set.");
        expenseListView.setCellFactory(listview -> new ExpenseListViewCell());
        logger.info("cell factory is set.");
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Expense} using a {@code ExpenseCard}.
     */
    class ExpenseListViewCell extends ListCell<Expense> {
        @Override
        protected void updateItem(Expense expense, boolean empty) {
            super.updateItem(expense, empty);

            if (empty || expense == null) {
                setGraphic(null);
                setText(null);
            } else {
                // setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
                String tmp = expense.getAmount() + expense.getDescription() + expense.getTimeString();
                setText(tmp);
            }
        }
    }
}

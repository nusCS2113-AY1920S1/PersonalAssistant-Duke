package duke.ui;

import duke.commons.LogsCenter;
import duke.logic.Logic;
import duke.model.Expense;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class ExpensePane extends UiPart<AnchorPane> {

    private static final Logger logger = LogsCenter.getLogger(ExpensePane.class);

    private static final String FXML_FILE_NAME = "ExpensePane.fxml";

    @FXML
    private Pane paneView;
    private PieChart pieChartSample;

    @FXML
    private ListView<Expense> expenseListView;

    @FXML
    private Label sortLabel;

    @FXML
    private Label viewLabel;

    @FXML
    private Label totalLabel;

    public Logic logic;
    public Set<String> tags;

    /**
     * Constructor for ExpensePane, the controller class for ExpensePane.fxml.
     * @param expenseList ObservableList of Expense objects.
     * @param logic the Logic Object of Duke
     * @param totalExpense StringProperty of the sum of displayed expenses
     * @param filterCriteria StringProperty of the current filter criteria of ExpenseList(broken for now, to be fixed in future builds)
     * @param sortCriteria StringProperty of the sortCriteria of ExpenseList
     * @param viewCriteria StringProperty of the viewCriteria of ExpenseList
     */
    public ExpensePane(ObservableList<Expense> expenseList, Logic logic,
                       StringProperty totalExpense,
                       StringProperty filterCriteria,
                       StringProperty sortCriteria,
                       StringProperty viewCriteria) {
        super(FXML_FILE_NAME, null);
        logger.info("expenseList has length " + expenseList.size());
        logger.info("expenseList has length " + expenseList.size());
        Label emptyExpenseListPlaceholder = new Label();
        emptyExpenseListPlaceholder.setText("No Expenses yet. "
                + "Type \"addExpense #amount\" to add one!");
        expenseListView.setPlaceholder(emptyExpenseListPlaceholder);
        expenseListView.setItems(expenseList);
        logger.info("Items are set.");
        expenseListView.setCellFactory(expenseListView -> new ExpenseListViewCell());
        logger.info("cell factory is set.");

        sortLabel.textProperty().bindBidirectional(sortCriteria);
        viewLabel.textProperty().bindBidirectional(viewCriteria);
        totalLabel.textProperty().bindBidirectional(totalExpense);

        this.logic = logic;
        PieChart pieChartSample = new PieChart();
        pieChartSample.setData(getData());
        paneView.getChildren().clear();
        pieChartSample.setTitle("Expenditure");
        paneView.getChildren().add(pieChartSample);
        logger.info("Pie chart is set.");
    }

    /**
     * Retrieves the amounts for each specific tag.
     *
     * @return ObservableList of type PieChart.data to be passed into the pie chart
     */
    private ObservableList<PieChart.Data> getData() {
        getTags();

        ObservableList<PieChart.Data> dataList = FXCollections.observableArrayList();

        for (Object tag : this.tags) {
            dataList.add(new PieChart.Data((String) tag, logic.getTagAmount((String) tag).doubleValue()));
        }
        return dataList;
    }

    /**
     * Retrieves all tags as shown in external list and stores in a set {@code tags}.
     */
    private void getTags() {
        tags = new HashSet<>();
        for (Expense expense : logic.getExternalExpenseList()) {
            if (!expense.getTag().isEmpty()) {
                tags.add(expense.getTag());
            }
        }
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PlanBot.PlanDialog}
     * using a {@code PlanBot.PlanDialog}.
     */
    class ExpenseListViewCell extends ListCell<Expense> {
        @Override
        protected void updateItem(Expense expense, boolean empty) {
            super.updateItem(expense, empty);
            if (empty || expense == null) {
                setGraphic(null);
                setText(null);
            } else {
                int index = expenseListView.getItems().indexOf(expense) + 1;
                setGraphic(new ExpenseCard(expense, index).getRoot());
            }
        }
    }


}

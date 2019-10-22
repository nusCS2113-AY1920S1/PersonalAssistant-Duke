package duke.ui;

import duke.commons.LogsCenter;
import duke.logic.Logic;
import duke.model.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class ExpensePane extends UiPart<AnchorPane> {

    private static final Logger logger = LogsCenter.getLogger(ExpensePane.class);

    private static final String FXML_FILE_NAME = "ExpensePane1.fxml";

    @FXML
    private Pane paneView;
    private PieChart pieChartSample;

    @FXML
    private ListView<Expense> expenseListView;

    public Logic logic;
    public Set<String> tags;

    public ExpensePane(ObservableList<Expense> expenseList, Logic logic) {
        super(FXML_FILE_NAME, null);
        logger.info("expenseList has length " + expenseList.size());
        expenseListView.setItems(expenseList);
        logger.info("Items are set.");
        expenseListView.setCellFactory(listview -> new ExpenseListViewCell());
        logger.info("cell factory is set.");
        this.logic = logic;
        PieChart pieChartSample = new PieChart();
        pieChartSample.setData(getData());
        paneView.getChildren().clear();
        pieChartSample.setTitle("Expenditure");
        paneView.getChildren().add(pieChartSample);
        logger.info("Pie chart is set.");
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

    private ObservableList<PieChart.Data> getData() {
        getTags();

        ObservableList<PieChart.Data> dataList = FXCollections.observableArrayList();

        for (Object tag : this.tags) {
            dataList.add(new PieChart.Data((String) tag, logic.getTagAmount((String) tag).doubleValue()));
        }
        //final PieChart chart = new PieChart(dataList);
        //chart.setTitle("Expenditure");

        return dataList;
    }

    private void getTags() {
        tags = new HashSet<>();
        for (Expense expense : logic.getExternalExpenseList()) {
            String[] tagsString = expense.getTagsString().split(" ");
            if (tagsString.length > 0) {
                tags.addAll(Arrays.asList(tagsString));
            }
        }
    }
}

package duke.ui;

import duke.commons.LogsCenter;
import duke.logic.Logic;
import duke.model.Expense;
import duke.model.ExpenseList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class PieChartSample extends UiPart<AnchorPane> {

    private static final Logger logger = LogsCenter.getLogger(PieChartSample.class);
    private static final String FXML_FILE_NAME = "PieChart.fxml";

    public Logic logic;
    public Set<String> tags;
    private PieChart pieChart;

    public PieChartSample(Logic logic) {
        super(FXML_FILE_NAME, null);
        this.logic = logic;
        this.pieChart = new PieChart();
        pieChart.setData(getData());
        logger.info("Pie chart is set.");
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
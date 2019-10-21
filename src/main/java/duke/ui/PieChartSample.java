package duke;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import duke.logic.CommandResult;
import duke.logic.Logic;
import duke.model.Expense;
import duke.model.ExpenseList;
import duke.ui.ExpensePane;
import duke.ui.UiPart;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class PieChartSample extends UiPart<AnchorPane> {

    private static final Logger logger = LogsCenter.getLogger(ExpensePane.class);

    public Logic logic;
    public Set<String> tags;
    private static final String FXML_FILE_NAME = "PieChart.fxml";

    public PieChartSample(Logic logic) {
        super(FXML_FILE_NAME, null);
        this.logic = logic;
    }

    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Expenditure");
        stage.setWidth(500);
        stage.setHeight(500);
        //setDuke(new Logic());
        getTags();

        ObservableList<PieChart.Data> dataList = FXCollections.observableArrayList();

        for (Object tag : tags) {
            dataList.add(new PieChart.Data((String) tag, logic.getTagAmount((String) tag).doubleValue()));
        }

        final PieChart chart = new PieChart(dataList);
        chart.setTitle("Expenditure");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
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
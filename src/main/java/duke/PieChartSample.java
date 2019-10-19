package duke;

import duke.dukeobject.Expense;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PieChartSample extends Application {

    public PieChartSample() {}

    public Duke duke;
    public Set<String> tags;


    public void setDuke(Duke d) {
        this.duke = d;
    }

    @Override
    public void start(Stage stage) {
            Scene scene = new Scene(new Group());
            stage.setTitle("Expenditure");
            stage.setWidth(500);
            stage.setHeight(500);
            setDuke(new Duke());
            getTags();

            ObservableList<PieChart.Data> dataList = FXCollections.observableArrayList();

            for (Object tag : tags) {
                dataList.add(new PieChart.Data((String) tag, duke.expenseList.getTagAmount((String) tag).doubleValue()));
            }

            final PieChart chart = new PieChart(dataList);
            chart.setTitle("Expenditure");

            ((Group) scene.getRoot()).getChildren().add(chart);
            stage.setScene(scene);
            stage.show();
    }

    private void getTags() {
        tags = new HashSet<String>();
        for (Expense expense: duke.expenseList.getExternalList()) {
            String[] tagsString = expense.getTagsString().split(" ");
            if(tagsString.length > 0) {
                tags.addAll(Arrays.asList(tagsString));

            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
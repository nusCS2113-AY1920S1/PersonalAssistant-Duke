//@@author qjie7

package duke.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PieChartBox {
    /**
     * Show PieChart.
     * @param frequencyList   a list of command frequency
     * @param commandNameList a list of command name
     */
    public static void showPieChartBox(ArrayList<Integer> frequencyList, ArrayList<String> commandNameList) {
        final Scene scene = new Scene(new Group());
        Stage stage = new Stage();
        stage.setTitle("Pie Chart");

        stage.setWidth(500);
        stage.setHeight(500);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (int i = 0; i < commandNameList.size(); i++) {
            pieChartData.add(new PieChart.Data(commandNameList.get(i), frequencyList.get(i)));
        }

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Command Frequency");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }
}

//@@author qjie7

package duke.gui;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Calendar;

public class BarChartBox {
    /**
     * Show BarChart.
     * @param frequencyList   a list of command frequency
     * @param commandNameList a list of command name
     */
    public static void showBarChartBox(ArrayList<Integer> frequencyList, ArrayList<String> commandNameList) {

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        final String yearInString = Integer.toString(year);
        Stage stage = new Stage();
        stage.setTitle("Bar Chart");

        bc.setTitle("Command Frequency");
        xAxis.setLabel("Command");
        yAxis.setLabel("Frequency");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(yearInString);

        for (int i = 0; i < commandNameList.size(); i++) {
            series1.getData().add(new XYChart.Data(commandNameList.get(i), frequencyList.get(i)));
        }

        Scene scene = new Scene(bc, 800, 600);
        bc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();
    }
}

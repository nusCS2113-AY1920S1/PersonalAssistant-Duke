package duke;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TrendingChart {

    @FXML
    private LineChart<?,?> lineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private ArrayList<String> dataX;
    private ArrayList<BigDecimal> dataY;

    private enum viewScope {
        DAILY, WEEKLY, MONTHLY, YEARLY;
    }

    /*
    public void updateLineChart() {
        XYChart.Series series = new XYChart.Series();
        series.getData().add()
    }

    private void updatedDailyData() {
        dataX.clear();
        dataY.clear();
        List<Expense> tempList =
        for(int i = 0; i < 6; i++) {

        }
    }
    */
}

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;

public class Histogram extends HBox {

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private BarChart<String, Number> barChart;

    //@@ cctt1014
    private Histogram(String title, String xName, String yName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Histogram.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        fxmlLoader.load();

        barChart.setTitle(title);
        xAxis.setLabel(xName);
        yAxis.setLabel(yName);
    }

    public static Histogram getHistogram(String title, ArrayList<String> xData, ArrayList<Float> yData) throws IOException {
        Histogram histogram = new Histogram(title, "Category", "Amount");
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        for (int index = 0; index < yData.size(); index++) {
            series.getData().add(new XYChart.Data<>(xData.get(index), yData.get(index)));
        }
        histogram.barChart.getData().add(series);
        return histogram;
    }

    public static Histogram getTwoSeriesHistogram(String title, ArrayList<String> xData, ArrayList<Float> yData1,
                                                  ArrayList<Float> yData2) throws IOException {
        Histogram histogram = new Histogram(title, "Month", "Amount");
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        for (int index = 0; index < yData1.size(); index++) {
            series1.getData().add(new XYChart.Data<>(xData.get(index), yData1.get(index)));
        }
        for (int index = 0; index < yData2.size(); index++) {
            series2.getData().add(new XYChart.Data<>(xData.get(index), yData2.get(index)));
        }
        series1.setName("Income");
        series2.setName("Expenditure");
        histogram.barChart.getData().add(series1);
        histogram.barChart.getData().add(series2);
        return histogram;
    }
}

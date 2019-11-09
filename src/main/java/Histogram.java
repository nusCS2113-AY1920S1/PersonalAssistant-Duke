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
    private CategoryAxis axisX;
    @FXML
    private NumberAxis axisY;
    @FXML
    private BarChart<String, Number> barChart;

    //@@author cctt1014
    private Histogram(String title, String nameX, String nameY) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Histogram.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        fxmlLoader.load();

        barChart.setTitle(title);
        axisX.setLabel(nameX);
        axisY.setLabel(nameY);
    }

    /**
     * This method receives and packs the input data into a one-series histogram and
     * returns the histogram.
     * @param title The title of the graph
     * @param dataX The data of the x coordinate
     * @param dataY The data of the y coordinate
     * @return The one-series histogram with the input data
     * @throws IOException The IOE exception
     */
    public static Histogram getHistogram(String title, ArrayList<String> dataX, ArrayList<Float> dataY)
            throws IOException {
        Histogram histogram = new Histogram(title, "Category", "Amount");
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        for (int index = 0; index < dataY.size(); index++) {
            series.getData().add(new XYChart.Data<>(dataX.get(index), dataY.get(index)));
        }
        histogram.barChart.getData().add(series);
        return histogram;
    }

    /**
     * This method receives and packs the input data into a two-series histogram and
     * returns the histogram.
     * @param title The title of the graph
     * @param dataX The data of the x coordinate
     * @param dataY1 The data of the y coordinate in the first series
     * @param dataY2 The data of the y coordinate in the second series
     * @return The two-series histogram with the input data
     * @throws IOException The IOE exception
     */
    public static Histogram getTwoSeriesHistogram(String title, ArrayList<String> dataX, ArrayList<Float> dataY1,
                                                  ArrayList<Float> dataY2) throws IOException {
        final Histogram histogram = new Histogram(title, "Month", "Amount");
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        for (int index = 0; index < dataY1.size(); index++) {
            series1.getData().add(new XYChart.Data<>(dataX.get(index), dataY1.get(index)));
        }
        for (int index = 0; index < dataY2.size(); index++) {
            series2.getData().add(new XYChart.Data<>(dataX.get(index), dataY2.get(index)));
        }
        series1.setName("Income");
        series2.setName("Expenditure");
        histogram.barChart.getData().add(series1);
        histogram.barChart.getData().add(series2);
        return histogram;
    }
}

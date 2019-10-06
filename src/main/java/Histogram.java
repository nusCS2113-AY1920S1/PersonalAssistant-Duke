import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;

import java.io.IOException;

import money.Account;

public class Histogram extends HBox {

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private BarChart<String, Number> barChart;

    private Histogram(String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Histogram.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        fxmlLoader.load();

        barChart.setTitle(title);
        xAxis.setLabel("Category");
        yAxis.setLabel("Amount");
    }

    public static Histogram getHistogram(String title, float in, float out) throws IOException {
        Histogram histogram = new Histogram(title);
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Income", in));
        series.getData().add(new XYChart.Data<>("Expenditure", out));
        histogram.barChart.getData().add(series);
        return histogram;
    }
}

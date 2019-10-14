import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class LineGraph extends HBox {
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LineChart<String, Number> lineChart;

    private LineGraph(String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Histogram.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        fxmlLoader.load();

        lineChart.setTitle(title);
        xAxis.setLabel("Category");
        yAxis.setLabel("Amount");
    }

    public static LineGraph getLineGraph(String title, String[] xData, float[] yData) throws IOException {
        LineGraph lineGraph = new LineGraph(title);
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        for (int index = 0; index < yData.length; index++) {
            series.getData().add(new XYChart.Data<>(xData[index], yData[index]));
        }
        lineGraph.lineChart.getData().add(series);
        return lineGraph;
    }
}

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;

public class LineGraph extends HBox {
    @FXML
    private CategoryAxis axisX;
    @FXML
    private NumberAxis axisY;
    @FXML
    private LineChart<String, Number> lineChart;

    //@@author cctt1014
    private LineGraph(String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/LineGraph.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        fxmlLoader.load();

        lineChart.setTitle(title);
        axisX.setLabel("Category");
        axisY.setLabel("Amount");
    }

    /**
     * This function produces a one-series line graph with the given parameter of data as the input.
     * It packs the data and the title into lineGraph variables then return it.
     * @param title The title of the graph
     * @param dataX The data of the x coordinate
     * @param dataY The data of the y coordinate
     * @return The one-series line graph with the input x,y data
     * @throws IOException The IOE exception
     */
    public static LineGraph getLineGraph(String title, ArrayList<String> dataX, ArrayList<Float> dataY)
            throws IOException {
        LineGraph lineGraph = new LineGraph(title);
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        for (int index = 0; index < dataY.size(); index++) {
            series.getData().add(new XYChart.Data<>(dataX.get(index), dataY.get(index)));
        }
        lineGraph.lineChart.getData().add(series);
        return lineGraph;
    }
}

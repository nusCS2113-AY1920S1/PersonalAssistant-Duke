import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;

public class CircleChart extends HBox {

    @FXML
    private PieChart chart = new PieChart();

    private CircleChart(String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/CircleChart.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        fxmlLoader.load();

        chart.setTitle(title);
    }

    public static CircleChart getCircleChart(String title, ArrayList<String> xData, ArrayList<Float> yData) throws IOException {
        CircleChart circleChart = new CircleChart(title);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (int i = 0; i < xData.size(); i++) {
            pieChartData.add(new PieChart.Data(xData.get(i), yData.get(i)));
        }
        circleChart.chart.setData(pieChartData);
        return circleChart;
    }
}

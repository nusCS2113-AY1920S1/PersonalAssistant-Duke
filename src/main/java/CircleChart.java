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

    //@@author cctt1014
    private CircleChart(String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/CircleChart.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        fxmlLoader.load();

        chart.setTitle(title);
    }

    /**
     * This function packs the input data and the title into a pie chart and 
     * returns the pie chart in order to show on the user interface.
     * @param title The title of the graph
     * @param dataX The data of the x coordinate
     * @param dataY The data of the y coordinate
     * @return The pie chart with the input x,y data
     * @throws IOException The IOE exception
     */
    public static CircleChart getCircleChart(String title, ArrayList<String> dataX, ArrayList<Float> dataY) 
            throws IOException {
        CircleChart circleChart = new CircleChart(title);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (int i = 0; i < dataX.size(); i++) {
            pieChartData.add(new PieChart.Data(dataX.get(i), dataY.get(i)));
        }
        circleChart.chart.setData(pieChartData);
        return circleChart;
    }
}

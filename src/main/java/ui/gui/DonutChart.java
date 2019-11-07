package ui.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DonutChart extends PieChart {
    private Circle hole;

    /**
     * Constructor for GUI Component DonutChart.
     */
    public DonutChart() {
        super(FXCollections.observableArrayList());
        this.hole = new Circle(50);
        this.hole.setFill(Color.valueOf("#ede7d1"));
    }

    private DonutChart(ObservableList<Data> pieChartData) {
        super(pieChartData);
        this.hole = new Circle(50);
        this.hole.setFill(Color.valueOf("#ede7d1"));
    }

    @Override
    protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
        super.layoutChartChildren(top, left, contentWidth, contentHeight);
        if (this.getData().size() > 0) {
            Node pie = this.getData().get(0).getNode();
            Pane parentChart = (Pane) pie.getParent();
            if (!parentChart.getChildren().contains(hole)) {
                matchHoleToChart();
                parentChart.getChildren().add(hole);
            }
        }
    }


    // @@author {Mudaafi}-reused
    // Solution below adapted from:
    // https://stackoverflow.com/questions/24121580/can-piechart-from-javafx-be-displayed-as-a-doughnut
    private void matchHoleToChart() {
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        for (PieChart.Data data: this.getData()) {
            Node node = data.getNode();
            Bounds bounds = node.getBoundsInParent();
            if (bounds.getMinX() < minX) {
                minX = bounds.getMinX();
            }
            if (bounds.getMinY() < minY) {
                minY = bounds.getMinY();
            }
            if (bounds.getMaxX() > maxX) {
                maxX = bounds.getMaxX();
            }
            if (bounds.getMaxY() > maxY) {
                maxY = bounds.getMaxY();
            }
        }
        this.hole.setCenterX(minX + (maxX - minX) / 2);
        this.hole.setCenterY(minY + (maxY - minY) / 2);
        this.hole.setRadius((maxX - minX) / 2.6);
    }

    /**
     * Creates a DonutChart GUI Component.
     * @param budgetedAmt The amount in Gold
     * @param expenditure The amount in Red
     * @return DonutChart Object
     */
    public static DonutChart createBalanceChart(double budgetedAmt, double expenditure) {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Budget left", budgetedAmt - expenditure),
                        new PieChart.Data("Expenses", expenditure));
        return new DonutChart(pieChartData);
    }
}

package statistics;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.util.ArrayList;

public class BarChart_AWT extends JFrame {
    ArrayList<MyClass> Stats = GraduateEmploymentDisplay.getStats();
    ArrayList<Double> percentage = new ArrayList<>();
    String degree;

    public BarChart_AWT(String applicationTitle, String chartTitle, String input) {
        super(applicationTitle);
        degree = input;
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Years",
                "Percentage",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);
        JFreeChart barChart1 = ChartFactory.createBarChart(
                chartTitle,
                "Years",
                "Percentage",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel chartPanel = new ChartPanel(barChart);
        ChartPanel chartPanel1 = new ChartPanel(barChart);

        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
        setContentPane(chartPanel1);

    }

    private CategoryDataset createDataset() {
        final String label = degree;
        final String label1 = "Biomedical Engineering";
       // final String salary = "salary";
        final String ford = "FORD";
        final String year_2018 = "2018";
        final String year_2017 = "2017";
        final String year_2016 = "2016";
        // final String safety = "safety";
        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();
        for (int i = 0; i < Stats.size(); i++) {
            if (Stats.get(i).getA().equals(degree)) {
                percentage.add(Stats.get(i).getB());
            }
        }
        dataset.addValue(percentage.get(0), label1, year_2018);
        dataset.addValue(percentage.get(1), label1, year_2017);
        dataset.addValue(percentage.get(2), label1, year_2016);
        //dataset.addValue( 5.0 , fiat , safety );

//        dataset.addValue(5.0, salary, year_2016);
//        dataset.addValue(6.0, salary, year_2017);
//        dataset.addValue(10.0, salary, year_2018);
//        dataset.addValue( 4.0 , audi , safety );
//
//        dataset.addValue( 4.0 , ford , speed );
//        dataset.addValue( 2.0 , ford , userrating );
//        dataset.addValue( 3.0 , ford , millage );
//        dataset.addValue( 6.0 , ford , safety );

        return dataset;
    }
}




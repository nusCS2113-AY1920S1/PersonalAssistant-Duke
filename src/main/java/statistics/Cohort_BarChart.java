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

/**
 * This class produces a BarChart to show the number of students in Year 1 for each degree for the years 2016-2018
 *
 */

public class Cohort_BarChart extends JFrame {
    ArrayList<CohortStats> cohortStats = CohortSize.getCohortStats();
    ArrayList<Integer> Male = new ArrayList<>();
    ArrayList<Integer> Female = new ArrayList<>();
    ArrayList<Integer> Total = new ArrayList<>();
    String degree;

    /**
     * Creates a new instance of the Cohort_BarChart and sets the String degree declared earlier to input
     *
     * @param applicationTitle  the frame title.
     * @param chartTitle the title displayed just above the bar chart
     * @param input the degree inputted by the user
     *
     */

    public Cohort_BarChart(String applicationTitle, String chartTitle, String input) {
        super(applicationTitle);
        degree = input;
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Years",
                "No. of Students",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    /**
     * Creates a dataset containing student statistics for the years 2016-2018 if the degree matches the user input
     * ArrayLists named Male, Female and Total are populated with the corresponding values
     *
     * @return  The dataset.
     */

    private CategoryDataset createDataset() {
        final String label = degree;

        // final String salary = "salary";
        final String male = "Male";
        final String female = "Female";
        final String total = "Total";
        final String year_2018 = "2018";
        final String year_2017 = "2017";
        final String year_2016 = "2016";
        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();
        for (int i = 0; i < cohortStats.size(); i++) {
            if (cohortStats.get(i).getA().equals(degree)) {
                Male.add(cohortStats.get(i).getB());
                Female.add(cohortStats.get(i).getC());
                Total.add(cohortStats.get(i).getD());
            }
        }
        dataset.addValue(Male.get(0), male, year_2016);
        dataset.addValue(Male.get(1), male, year_2017);
        dataset.addValue(Male.get(2), male, year_2018);

        dataset.addValue(Female.get(0), female, year_2016);
        dataset.addValue(Female.get(1), female, year_2017);
        dataset.addValue(Female.get(2), female, year_2018);

        dataset.addValue(Total.get(0), total, year_2016);
        dataset.addValue(Total.get(1), total, year_2017);
        dataset.addValue(Total.get(2), total, year_2018);

        return dataset;
    }
}


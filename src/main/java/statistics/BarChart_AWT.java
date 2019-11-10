package statistics;
import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;

/**
 * This demo shows how to create a dual axis bar chart.  A workaround is used because the
 * {@link BarRenderer} and {@link CategoryAxis} classes will overlap the bars for the two
 * datasets - to get around this, an an additional series (containing 'null' values) is added
 * to each dataset, and the getLegendItems() method in the plot is overridden.
 *
 */
public class BarChart_AWT extends JFrame {
    ArrayList<MyClass> cohortStats = GraduateEmploymentDisplay.getStats();
    ArrayList<Double> percentage = new ArrayList<>();
    ArrayList<Integer> salary = new ArrayList<>();
    private String degree;
    private String title;
    /**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public BarChart_AWT(final String title, String input_degree) {
        super(title);
        degree = input_degree;
        final CategoryDataset dataset1 = createDataset1();
        final CategoryDataset dataset2 = createDataset2();
        final JFreeChart chart = createChart(dataset1, dataset2);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }


    /**
     * Creates a sample dataset.
     *
     * @return  The dataset.
     */
    private CategoryDataset createDataset1() {

        // row keys...
        final String series1 = "Salary Rates";
        final String series2 = "Dummy 1";

        // column keys...
        final String year_2018 = "2018";
        final String year_2017 = "2017";
        final String year_2016 = "2016";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(int i = 0; i < cohortStats.size(); i++) {
            if(degree.equals(cohortStats.get(i).getA())) {
                percentage.add(cohortStats.get(i).getB());
                salary.add(cohortStats.get(i).getC());
            }
        }

        dataset.addValue(salary.get(2), series1, year_2016);
        dataset.addValue(salary.get(1), series1, year_2017);
        dataset.addValue(salary.get(0), series1, year_2018);


        dataset.addValue(null, series2, year_2016);
        dataset.addValue(null, series2, year_2017);
        dataset.addValue(null, series2, year_2018);

        return dataset;

    }

    /**
     * Creates a sample dataset.
     *
     * @return  The dataset.
     */
    private CategoryDataset createDataset2() {

        // row keys...
        final String series1 = "Dummy 2";
        final String series2 = "Employment percentage rates";

        // column keys...
        final String year_2018 = "2018";
        final String year_2017 = "2017";
        final String year_2016 = "2016";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(null, series1,  year_2016 );
        dataset.addValue(null, series1,  year_2017 );
        dataset.addValue(null, series1,  year_2018 );

        dataset.addValue(percentage.get(2), series2,  year_2016 );
        dataset.addValue(percentage.get(1), series2,  year_2017 );
        dataset.addValue(percentage.get(0), series2,  year_2018 );

        return dataset;

    }

    /**
     * Creates a chart.
     *
     * @param dataset1  the first dataset.
     * @param dataset2  the second dataset.
     *
     * @return A chart.
     */
    private JFreeChart createChart(final CategoryDataset dataset1, final CategoryDataset dataset2) {

        final CategoryAxis domainAxis = new CategoryAxis("Years");
        final NumberAxis rangeAxis = new NumberAxis("Basic Mean Salary");
        final BarRenderer renderer1 = new BarRenderer();
        renderer1.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        final CategoryPlot plot = new CategoryPlot(dataset1, domainAxis, rangeAxis, renderer1) {

            /**
             * Override the getLegendItems() method to handle special case.
             *
             * @return the legend items.
             */
            public LegendItemCollection getLegendItems() {

                final LegendItemCollection result = new LegendItemCollection();

                final CategoryDataset data = getDataset();
                if (data != null) {
                    final CategoryItemRenderer r = getRenderer();
                    if (r != null) {
                        final LegendItem item = r.getLegendItem(0, 0);
                        result.add(item);
                    }
                }

                // the JDK 1.2.2 compiler complained about the name of this
                // variable
                final CategoryDataset dset2 = getDataset(1);
                if (dset2 != null) {
                    final CategoryItemRenderer renderer2 = getRenderer(1);
                    renderer2.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
                    if (renderer2 != null) {
                        final LegendItem item = renderer2.getLegendItem(1, 1);
                        result.add(item);
                    }
                }

                return result;

            }

        };

        if(degree.equals("bme"))
             title = "Biomedical Engineering";
        if(degree.equals("ceg"))
             title = "Computer Engineering";
        if(degree.equals("che"))
             title = "Chemical Engineering";
        if(degree.equals("mse"))
             title = "Materials Science Engineering";
        if(degree.equals("enve"))
             title = "Environmental Engineering";
        if(degree.equals("cive"))
             title = "Civil Engineering";
        if(degree.equals("ise"))
             title = "Industrial Systems Engineering";
        if(degree.equals("me"))
             title = "Mechanical Engineering";
        if(degree.equals("ee"))
             title = "Electrical Engineering";

        final JFreeChart chart = new JFreeChart(title, plot);
        chart.setBackgroundPaint(Color.white);
//        chart.getLegend().setAnchor(Legend.SOUTH);
        plot.setBackgroundPaint(new Color(0xEE, 0xEE, 0xFF));
        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        plot.setDataset(1, dataset2);
        plot.mapDatasetToRangeAxis(1, 1);
        final ValueAxis axis2 = new NumberAxis("Employment Percentage Rates");
        plot.setRangeAxis(1, axis2);
        plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
        final BarRenderer renderer2 = new BarRenderer();
        plot.setRenderer(1, renderer2);

        return chart;
    }

}

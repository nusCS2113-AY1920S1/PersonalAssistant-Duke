package statistics;

import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;

public class CohortSizeDisplay {
    private static ArrayList<CohortStats> cohortStats = new ArrayList<>();
    public static ArrayList<CohortStats> getCohortStats(){
        return cohortStats;
    }

    public void print(String input) {
        for (int i = 0; i < cohortStats.size(); i++) {
            if (cohortStats.get(i).getA().equals(input)) {
                System.out.println(cohortStats.get(i).getB() + " " + cohortStats.get(i).getC() + " " + cohortStats.get(i).getD());
            }
        }
        BarChart chart = new BarChart("Cohort Size Statistics",
                "Cohort Size", input);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}

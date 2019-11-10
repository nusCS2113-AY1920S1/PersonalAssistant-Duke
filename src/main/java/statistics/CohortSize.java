package statistics;

import exception.DukeException;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.List;

public class CohortSize implements Statistics  {
    private static ArrayList<CohortStats> cohortStats = new ArrayList<>();
    String title;
    public static ArrayList<CohortStats> getCohortStats(){
        return cohortStats;
    }

    @Override
    public void loadStatistics(List<String> st) throws DukeException {
        for (int i = 1; i < 4; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                cohortStats.add(new CohortStats("che", Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            }
        }
        for (int i = 5; i < 8; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                cohortStats.add(new CohortStats("enve", Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            }
        }
        for (int i = 9; i < 12; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                cohortStats.add(new CohortStats("mse", Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            }
        }
        for (int i = 13; i < 16; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                cohortStats.add(new CohortStats("bme", Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            }
        }
        for (int i = 17; i < 20; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                cohortStats.add(new CohortStats("cive", Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            }
        }
        for (int i = 21; i < 24; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                cohortStats.add(new CohortStats("ee", Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            }
        }
        for (int i = 25; i < 28; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                cohortStats.add(new CohortStats("ise", Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            }
        }
        for (int i = 29; i < 32; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                cohortStats.add(new CohortStats("me", Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            }
        }
        for (int i = 33; i < 36; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                cohortStats.add(new CohortStats("ceg", Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            }
        }


    }

    @Override
    public void print(String input) {

        if(input.equals("bme"))
            title = "Biomedical Engineering";
        if(input.equals("ceg"))
            title = "Computer Engineering";
        if(input.equals("che"))
            title = "Chemical Engineering";
        if(input.equals("mse"))
            title = "Materials Science Engineering";
        if(input.equals("enve"))
            title = "Environmental Engineering";
        if(input.equals("cive"))
            title = "Civil Engineering";
        if(input.equals("ise"))
            title = "Industrial Systems Engineering";
        if(input.equals("me"))
            title = "Mechanical Engineering";
        if(input.equals("ee"))
            title = "Electrical Engineering";
        Cohort_BarChart chart = new Cohort_BarChart("Cohort Size Statistics",
                title, input);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }

}

package statistics;

import exception.DukeException;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.List;

public class GraduateEmployment implements Statistics {
    private static ArrayList<GraduateStats> Stats = new ArrayList<>();
    public static ArrayList<GraduateStats> getStats() {
        return Stats;
    }


    @Override
    public void loadStatistics(List<String> st) throws DukeException {
        for (int i = 1; i < 4; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new GraduateStats("bme", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 5; i < 8; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new GraduateStats("che", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 9; i < 12; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new GraduateStats("cive", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 13; i < 16; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new GraduateStats("ee", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 17; i < 20; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new GraduateStats("enve", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 21; i < 24; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new GraduateStats("ise", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 25; i < 28; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new GraduateStats("mse", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 29; i < 32; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new GraduateStats("me", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 33; i < 36; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new GraduateStats("ceg", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
    }

    @Override
    public void print(String input) {
        for (int i = 0; i < Stats.size(); i++) {
            if (Stats.get(i).getA().equals(input)) {
                System.out.println(Stats.get(i).getB() + " " + Stats.get(i).getC());
            }
        }
        final Employment_BarChart demo = new Employment_BarChart("Basic Mean Salary and Employment rates", input);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
}




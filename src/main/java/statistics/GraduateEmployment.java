package statistics;

import exception.DukeException;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * GraduateEmployment Class contains the employment rates of recent graduates and their basic mean salary
 * It implements the interface Statistics and uses the methods declared in it
 * It initialises an ArrayList of type GraduateStats in which the information is stored.
 * The Stats ArrayList allows to store 4 types of data - degree name, employment rate, basic mean salary
 * The class also initialises the Employment_BarChart which then displays the information in the form of a Dual Axis Bar Chart
 *
 */

public class GraduateEmployment implements Statistics {
    private static ArrayList<GraduateStats> Stats = new ArrayList<>();

    /**
     * Returns the ArrayList Stats when the method is called
     *
     * @param
     * @return cohortStats the ArrayList that contains the employment statistics
     *
     */

    public static ArrayList<GraduateStats> getStats() {
        return Stats;
    }

    /**
     * The method is derived from the Statistics Interface
     * The ArrayList Stats is loaded with the employment statistics for each particular degree
     *
     * @param st A list consisting of strings that have been read from the text file
     * @return
     *
     */
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

    /**
     * The method is derived from the Statistics Interface
     * The method creates an instance of Employment_BarChart
     * A Bar Chart is displayed to the users on a separate window
     *
     * @param input An input string which is the degree inputted by the user
     * @return
     *
     */
    @Override
    public void print(String input) {
        final Employment_BarChart demo = new Employment_BarChart("Basic Mean Salary and Employment rates", input);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}



